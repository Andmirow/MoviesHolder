package com.example.moviesholder.domain.example1

import dagger.Component


@Component
interface NewComponent {

    fun inject(activity: Activity)
}
