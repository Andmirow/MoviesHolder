package com.example.moviesholder.data.film_object.movie.film_model

data class FilmModel(
    val logo: Logo?,
    val poster: Poster?,
    val rating : Rating?,
    val votes : Votes?,
    val id : Int,
    val alternativeName : String?,
    val description : String?,
    val enName : String?,
    val movieLength : Int?,
    val name : String?,
    val names : List<NameFilm>?,
    val shortDescription : String?,
    val type : String?,
    val year : Int?
)