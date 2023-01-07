package com.example.moviesholder.domain

import android.app.Application
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.di.DaggerFilmComponent
import javax.inject.Inject

class FilmApp :Application() {

    @Inject
    lateinit var filmApi : FilmApi


    override fun onCreate() {
        DaggerFilmComponent.builder().build().inject(this)
        super.onCreate()
    }
}