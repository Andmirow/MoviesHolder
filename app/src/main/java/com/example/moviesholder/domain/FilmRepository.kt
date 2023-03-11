package com.example.moviesholder.domain

import androidx.paging.PagingData
import com.example.moviesholder.data.room.database.FilmsDb
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    suspend fun deleteFilm(film : Film)

    suspend fun saveFavoriteFilm(film : Film)

    fun getFavoriteFilms(): Flow<PagingData<FilmsDb.FilmDbModel>>

    fun getFilms(): Flow<PagingData<FilmsDb.FilmDbModel>>

    suspend fun refresh()









}