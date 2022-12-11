package com.example.moviesholder.domain.example1



class Activity {


    lateinit var keyboard: Keyboard

    init {

        DaggerNewComponent.create().inject(this)
//        val component = Component()
//        component.inject(this)
    }
}
