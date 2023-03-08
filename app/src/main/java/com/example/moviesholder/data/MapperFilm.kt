package com.example.moviesholder.data

import com.example.moviesholder.data.retrofit.film_model.Doc
import com.example.moviesholder.data.retrofit.film_model.FilmModelList
import com.example.moviesholder.data.room.database.FilmsDb
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
                isFavorite = false
            )
        }


        fun mapDocToFilmDbModel(doc : Doc): FilmsDb.FilmDbModel {
            val poster = doc.poster
            val rating = doc.rating
            val ratingString = rating.imdb.toString()
            return doc.let {
                FilmsDb.FilmDbModel(
                    id_Retrofit = it.id,
                    id = 0,
                    name = it.name,
                    poster = poster?.url!!,
                    rating= ratingString,
                    description= it.description,
                    isFavorite = false
                )
            }
        }




        fun mapFilmDbModelToFilm(film : FilmsDb.FilmDbModel): Film {
            val posterUrl = film.poster
            val rating = film.rating
            return Film(idRoom = film.id,
                idRetrofit = film.id_Retrofit,
                name = film.name,
                poster = posterUrl,
                rating = rating,
                description = film.description,
                isFavorite = film.isFavorite
            )
        }

        fun mapFilmsRetroToFilmsDb(filmModelList : FilmModelList): FilmsDb{
            val listFilm = filmModelList.docs.map{
                mapDocToFilmDbModel(it)
            }
            return FilmsDb(
                movies = listFilm,
                page = filmModelList.page,
                pages = filmModelList.pages,
            )
        }


        fun mapFilmToFilmDbModel(film : Film): FilmsDb.FilmDbModel =
            film.let {
                FilmsDb.FilmDbModel(
                    id_Retrofit = it.idRetrofit,
                    id = it.idRoom,
                    name = it.name!!,
                    poster = it.poster!!,
                    rating= it.rating!!,
                    description= it.description!!,
                    isFavorite = it.isFavorite
                )
            }

        fun mapListDbItemToEmtity(listItemDbModel : List<FilmsDb.FilmDbModel>) = listItemDbModel.map {
            mapFilmDbModelToFilm(it)
        }

        fun mapListDocToFilm(listDoc : List<Doc>, page : Int) = listDoc.map {
            mapDocToFilm(it,page)
        }

    }
}