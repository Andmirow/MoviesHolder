package com.example.moviesholder.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.domain.example1.Keyboard
import com.example.moviesholder.presentation.MainViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = [RetrofitModule::class])
interface FilmComponent {

    fun getFilmApi() : FilmApi

    fun inject(activity: Fragment)
}