package com.example.moviesholder.data.retrofit.film_object.movie

data class FilmListModel(
    val docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)