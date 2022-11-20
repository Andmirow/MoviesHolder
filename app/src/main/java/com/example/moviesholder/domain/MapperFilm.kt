package com.example.moviesholder.domain

import com.example.moviesholder.data.retrofit.film_model.Doc

class MapperFilm {
    companion object{

        fun mapDocToFilm(doc : Doc): Film{
            val poster = doc.poster
            val posterUrl = poster?.url

            val rating = doc.rating
            val ratingString = rating.imdb.toString()

            return Film(doc.name, posterUrl,ratingString,doc.description)
        }






    }
}