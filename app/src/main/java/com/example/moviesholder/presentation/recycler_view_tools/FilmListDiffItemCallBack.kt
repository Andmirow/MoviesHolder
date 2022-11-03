package com.example.moviesholder.presentation.recycler_view_tools

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesholder.data.retrofit.film_object.movie.Doc

class FilmListDiffItemCallBack : DiffUtil.ItemCallback<Doc>() {

    override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return oldItem == newItem
    }
}