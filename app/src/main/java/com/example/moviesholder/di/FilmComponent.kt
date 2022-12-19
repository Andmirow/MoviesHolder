package com.example.moviesholder.di

import com.example.moviesholder.domain.example1.ComputerModule
import dagger.Component


@Component(modules = [RetrofitModule::class, ComputerModule::class ])
interface FilmComponent {

    fun inject(activity: FilmApp)

}