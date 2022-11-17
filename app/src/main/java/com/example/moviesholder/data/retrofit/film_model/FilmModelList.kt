package com.example.moviesholder.data.retrofit.film_model

data class FilmModelList(
    val docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)