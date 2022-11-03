package com.example.moviesholder.presentation.recycler_view_tools

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.moviesholder.R
import com.example.moviesholder.data.retrofit.film_object.movie.Doc
import com.example.moviesholder.databinding.ItemFilmCardBinding
import com.example.moviesholder.databinding.ItemFilmCardBindingImpl
import kotlinx.android.synthetic.main.item_film_card.view.*

class FilmAdapter : ListAdapter<Doc, FilmViewHolder>(FilmListDiffItemCallBack()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {

        val binding = ItemFilmCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //val binding1 = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.id.film_card,parent,false)//chek viewType
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        val binding = holder.binding

        Glide.with(binding.root.photo.context)
            .load(film.poster.previewUrl)
            .centerCrop()
            .into(binding.root.photo)

    }

}