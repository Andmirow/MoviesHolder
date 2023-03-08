package com.example.moviesholder.di

import androidx.paging.ExperimentalPagingApi
import com.example.moviesholder.data.RemoteRepositoryImpl
import com.example.moviesholder.domain.FilmRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindFilmsRepository(
        filmsRepository: RemoteRepositoryImpl
    ): FilmRepository



}