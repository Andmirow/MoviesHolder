package com.example.moviesholder.data.retrofit


import com.example.moviesholder.data.retrofit.film_model.FilmModelList
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
        @Query("sortType") sortType : String,
        @Query("page") page : String,
        @Query("limit") limit : String
    ): Single<FilmModelList>

}