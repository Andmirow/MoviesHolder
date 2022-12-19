package com.example.moviesholder.domain.example1

import com.example.moviesholder.data.retrofit.FilmApi
import javax.inject.Inject


class Activity {

    @Inject
    lateinit var keyboard: Keyboard

    @Inject
    lateinit var memory: Memory

    @Inject
    lateinit var filmApi : FilmApi

    init {

        DaggerNewComponent.create().inject(this)

    }
}
