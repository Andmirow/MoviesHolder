package com.example.moviesholder.domain

import androidx.lifecycle.LiveData

interface FilmRepository {

    fun getListFilm() : List<Film>
    fun deleteFilm(film : Film)
    fun findFilmByIdRoom(id : Int) : Film
    fun findFilmByIdRetrofit(id : Int) : Film?
    fun addFilm(film : Film)
}