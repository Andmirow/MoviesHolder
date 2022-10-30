package com.example.moviesholder.data.retrofit.film_object.movie

data class Rating(
    val _id: String,
    val await: Int,
    val filmCritics: Double,
    val imdb: Double,
    val kp: Double,
    val russianFilmCritics: Int
)