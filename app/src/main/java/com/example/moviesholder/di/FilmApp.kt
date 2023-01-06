package com.example.moviesholder.di

import android.app.Application
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.domain.example1.Keyboard
import com.example.moviesholder.domain.example1.Memory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class FilmApp :Application() {

    @Inject
    lateinit var filmApi : FilmApi


    override fun onCreate() {
        DaggerFilmComponent.builder().build().inject(this)
        super.onCreate()
        //configureRetrofit()

    }


    private fun configureRetrofit(){
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(" https://api.kinopoisk.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        filmApi = retrofit.create(FilmApi::class.java)

    }


}