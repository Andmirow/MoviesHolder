package com.example.moviesholder.data

import com.example.moviesholder.data.retrofit.film_model.Doc
import com.example.moviesholder.data.room.database.FilmDbModel
import com.example.moviesholder.domain.Film

class MapperFilm {
    companion object{

        fun mapDocToFilm(doc : Doc, page : Int): Film {
            val poster = doc.poster
            val posterUrl = poster?.url
            val rating = doc.rating
            val ratingString = rating.imdb.toString()
            return Film(
                idRetrofit = doc.id,
                idRoom = 0,
                name = doc.name,
                poster = posterUrl,
                rating = ratingString,
                description = doc.description,
                page = page,
                isFavorite = false
            )
        }

        fun mapFilmDbModelToFilm(film : FilmDbModel): Film {
            val posterUrl = film.poster
            val rating = film.rating
            return Film(idRoom = film.id,
                idRetrofit = film.id_Retrofit,
                name = film.name,
                poster = posterUrl,
                rating = rating,
                description = film.description,
                page = film.page,
                isFavorite = film.isFavorite
            )
        }

        fun mapFilmToFilmDbModel(film : Film): FilmDbModel =
            film.let {
                FilmDbModel(
                    id_Retrofit = it.idRetrofit,
                    id = it.idRoom,
                    name = it.name!!,
                    poster = it.poster!!,
                    rating= it.rating!!,
                    description= it.description!!,
                    page = it.page,
                    isFavorite = it.isFavorite
                )
            }

        fun mapListDbItemToEmtity(listItemDbModel : List<FilmDbModel>) = listItemDbModel.map {
            mapFilmDbModelToFilm(it)
        }

        fun mapListDocToFilm(listDoc : List<Doc>, page : Int) = listDoc.map {
            mapDocToFilm(it,page)
        }

    }
}