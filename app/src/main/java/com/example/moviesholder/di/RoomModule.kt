package com.example.moviesholder.di

import android.app.Application
import android.content.Context
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmListDao
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@Module
class RoomModule() {

    @Provides
    fun provideCompositeDisposable() : CompositeDisposable = CompositeDisposable()




}