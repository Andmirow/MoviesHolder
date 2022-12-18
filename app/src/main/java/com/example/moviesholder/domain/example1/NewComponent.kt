package com.example.moviesholder.domain.example1


import dagger.Component


@Component(modules = [ComputerModule::class])
interface NewComponent {

    fun getKeyboard() : Keyboard

    fun inject(activity: Activity)
}
