package com.example.moviesholder.domain

import androidx.paging.PagingData
import com.example.moviesholder.data.room.database.FilmsDb
import io.reactivex.Flowable
import io.reactivex.Single

interface FilmRepository {

    fun getListFilm() : Single<List<FilmsDb.FilmDbModel>>
    fun deleteFilm(film : Film): Single<Int>
    fun findFilmByIdRoom(id : Int) : Film
    fun findFilmByIdRetrofit(id : Int) : Film?
    fun addFilm(film : Film): Single<Unit>


    fun getMovies(): Flowable<PagingData<FilmsDb.FilmDbModel>>





}