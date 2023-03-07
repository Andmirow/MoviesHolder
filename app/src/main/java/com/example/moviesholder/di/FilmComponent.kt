package com.example.moviesholder.di

import com.example.moviesholder.data.ItemRepositoryImpl
import com.example.moviesholder.domain.FilmApp
import com.example.moviesholder.presentation.MainViewModel
import dagger.Component


@Component(modules = [RetrofitModule::class, RoomModule::class ])
interface FilmComponent {

    fun inject(activity: FilmApp)

    fun inject(itemRepositoryImpl: ItemRepositoryImpl)

    fun inject(viewModel: MainViewModel)
}