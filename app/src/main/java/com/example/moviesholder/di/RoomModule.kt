package com.example.moviesholder.di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class RoomModule {

    @Provides
    fun provideCompositeDisposable() : CompositeDisposable = CompositeDisposable()




}