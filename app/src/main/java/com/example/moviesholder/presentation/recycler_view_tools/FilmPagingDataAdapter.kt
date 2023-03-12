package com.example.moviesholder.presentation.recycler_view_tools

import android.media.AudioRecord.MetricsConstants.SOURCE
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesholder.R
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.databinding.ItemFilmCardBinding
import com.example.moviesholder.domain.Film


class FilmPagingDataAdapter(
    private val onClickListener : ((Film)-> Unit),
    private val onLongClickListener : ((Film)-> Unit)
) : PagingDataAdapter<Film, FilmViewHolder>(
    FilmListDiffItemCallBack()
) {

    private lateinit var binding : ItemFilmCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        binding = ItemFilmCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        Log.i("film_1", "film ${film.toString()}")
        val binding = holder.binding
        when (binding){
            is ItemFilmCardBinding -> {
                if (film != null) {
                    Glide.with(binding.photo.context)
                        .load(film.poster)
                        .centerCrop()

                        .placeholder(R.drawable.ic_no_image)
                        .into(binding.photo)
                }
            }
        }
        binding.root.setOnClickListener {
            Log.e("TAG", "НАЖАЛ")
            if (film != null) {
                onClickListener(film)
            }
        }
        binding.root.setOnLongClickListener {
            Log.e("TAG", "сильно НАЖАЛ")
            if (film != null) {
                onLongClickListener(film)
            }
            return@setOnLongClickListener true
        }

    }




}


