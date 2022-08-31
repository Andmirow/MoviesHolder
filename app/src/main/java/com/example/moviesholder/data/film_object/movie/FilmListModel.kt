package com.example.moviesholder.data.film_object.movie

import com.example.moviesholder.data.film_object.movie.film_model.FilmModel

data class FilmListModel(
    val docs : List<FilmModel>,
    val total : Int,
    val limit : Int,
    val page : Int,
    val pages : Int
    )