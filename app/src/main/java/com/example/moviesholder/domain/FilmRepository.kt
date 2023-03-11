package com.example.moviesholder.domain

import androidx.paging.PagingData
import com.example.moviesholder.data.room.database.FilmsDb
import io.reactivex.Flowable
import io.reactivex.Single

interface FilmRepository {

    fun deleteFilm(film : Film)

    fun saveFavoriteFilm(film : Film)

    fun getFavoriteFilms(): Flowable<PagingData<FilmsDb.FilmDbModel>>

    fun getFilms(): Flowable<PagingData<FilmsDb.FilmDbModel>>

    fun refresh()

}