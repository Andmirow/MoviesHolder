package com.example.moviesholder.domain.example1


import android.app.Application
import com.example.moviesholder.di.FilmApp
import com.example.moviesholder.di.RetrofitModule
import dagger.Component


@Component(modules = [ComputerModule::class, RetrofitModule::class])
interface NewComponent {

    //fun getKeyboard() : Keyboard

    fun inject(activity: Activity)

    fun inject(application: FilmApp)

}
