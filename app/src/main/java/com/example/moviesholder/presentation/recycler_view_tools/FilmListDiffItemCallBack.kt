package com.example.moviesholder.presentation.recycler_view_tools

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesholder.domain.Film


class FilmListDiffItemCallBack : DiffUtil.ItemCallback<Film>() {

    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.idRetrofot == newItem.idRetrofot && oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}