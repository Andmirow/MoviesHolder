package com.example.moviesholder.data.rfrxok

import android.app.Application
import com.example.moviesholder.data.rfrxok.data.remote.FilmApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FilmApp :Application() {

    lateinit var filmApi : FilmApi


    override fun onCreate() {
        super.onCreate()

        configureRetrofit()


        val test = ""

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