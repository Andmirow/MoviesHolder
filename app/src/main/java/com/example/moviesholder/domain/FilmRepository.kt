package com.example.moviesholder.domain

import androidx.lifecycle.LiveData

interface FilmRepository {

    fun getList() : LiveData<List<Film>>
    suspend fun deleteFilm(film : Film)
    suspend fun setFilm(film : Film)
    suspend fun findFilmById(id : Int) : Film
    suspend fun addFilm(film : Film)
}