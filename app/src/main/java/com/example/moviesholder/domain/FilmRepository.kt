package com.example.moviesholder.domain

import com.example.moviesholder.data.room.database.FilmDbModel
import io.reactivex.Single

interface FilmRepository {

    fun getListFilm() : Single<List<FilmDbModel>>
    fun deleteFilm(film : Film): Single<Int>
    fun findFilmByIdRoom(id : Int) : Film
    fun findFilmByIdRetrofit(id : Int) : Film?

    fun addFilm(film : Film): Single<Unit>
}