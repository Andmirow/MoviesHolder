package com.example.moviesholder.presentation.recycler_view_tools

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.moviesholder.databinding.ItemFilmCardBinding
import com.example.moviesholder.domain.Film


class FilmAdapter(private val onClickListener : ((Film)-> Unit),private val onLongClickListener : ((Film)-> Unit)) : ListAdapter<Film, FilmViewHolder>(FilmListDiffItemCallBack()) {

    lateinit var binding : ItemFilmCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        binding = ItemFilmCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        val binding = holder.binding
        when (binding){
            is ItemFilmCardBinding -> {
                    Glide.with(binding.photo.context)
                        .load(film.poster)
                        .centerCrop()
                        .into(binding.photo)
            }
        }
        binding.root.setOnClickListener {
            Log.e("TAG", "НАЖАЛ")
            onClickListener(film)
        }
        binding.root.setOnLongClickListener {
            Log.e("TAG", "сильно НАЖАЛ")
            onLongClickListener(film)
            return@setOnLongClickListener true
        }

    }
}