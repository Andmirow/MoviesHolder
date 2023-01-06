package com.example.moviesholder.data

import com.example.moviesholder.data.retrofit.film_model.Doc
import com.example.moviesholder.data.room.database.FilmDbModel
import com.example.moviesholder.domain.Film

class MapperFilm {
    companion object{

        fun mapDocToFilm(doc : Doc): Film {
            val poster = doc.poster
            val posterUrl = poster?.url
            val rating = doc.rating
            val ratingString = rating.imdb.toString()
            return Film(
                idRetrofot = doc.id,
                idRoom = 0,
                name = doc.name,
                poster = posterUrl,
                rating = ratingString,
                description = doc.description
            )
        }

        fun mapFilmDbModelToFilm(film : FilmDbModel): Film {
            val posterUrl = film.poster
            val rating = film.rating
            return Film(idRoom = film.id,
                idRetrofot = film.id_Retrofit,
                name = film.name,
                poster = posterUrl,
                rating = rating,
                description = film.description
            )
        }

        fun mapFilmToFilmDbModel(film : Film): FilmDbModel =
            film.let {
                FilmDbModel(
                    id_Retrofit = it.idRetrofot,
                    id = it.idRoom,
                    name = it.name!!,
                    poster = it.poster!!,
                    rating= it.rating!!,
                    description= it.description!!
                )
            }

        fun mapListDbItemToEmtity(listItemDbModel : List<FilmDbModel>) = listItemDbModel.map {
            mapFilmDbModelToFilm(it)
        }




    }
}