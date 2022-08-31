package com.example.moviesholder.data.rfrxok.data.remote

import com.example.moviesholder.data.film_object.movie.FilmListModel
import io.reactivex.Single
import retrofit2.http.*

interface FilmApi {

    @GET("./movie")
    @Headers("Content-Type: application/json")
    fun getFilmList(
        @Query("token") token : String,
        @Query("search") search : String,
        @Query("field") field : String,
        @Query("sortField") sortField : String,
        @Query("sortType") sortType : String) : Single<FilmListModel>




}