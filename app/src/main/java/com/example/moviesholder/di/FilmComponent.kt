package com.example.moviesholder.di

import androidx.paging.ExperimentalPagingApi
import com.example.moviesholder.data.ItemRepositoryImpl
import com.example.moviesholder.domain.FilmApp
import com.example.moviesholder.presentation.MainViewModel
import com.example.moviesholder.presentation.RxViewModel
import dagger.Component


@OptIn(ExperimentalPagingApi::class)
@Component(modules = [RetrofitModule::class, RoomModule::class,RepositoriesModule::class])
interface FilmComponent {

    fun inject(activity: FilmApp)

    //fun inject(itemRepositoryImpl: ItemRepositoryImpl)

    fun inject(viewModel: MainViewModel)

    fun inject(viewModel: RxViewModel)

}