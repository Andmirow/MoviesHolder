package com.example.moviesholder.presentation.recycler_view_tools

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.moviesholder.data.retrofit.film_model.Doc
import com.example.moviesholder.databinding.ItemFilmCardBinding


class FilmAdapter : ListAdapter<Doc, FilmViewHolder>(FilmListDiffItemCallBack()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        Log.i("FilmInfo",film.toString())
        val binding = holder.binding

        when (binding){
            is ItemFilmCardBinding -> {
                    Glide.with(binding.photo.context)
                        .load(film.poster?.url)
                        .centerCrop()
                        .into(binding.photo)
                Log.i("FilmInfo2",binding.photo.context.toString())
            }
        }

    }

}