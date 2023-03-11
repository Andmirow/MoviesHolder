package com.example.moviesholder.data.room.database

import androidx.paging.PagingSource
import androidx.room.*
import io.reactivex.Single

@Dao
interface FilmListDao {

    @Query("DELETE FROM films WHERE id=:itemId AND isFavorite")
    fun deleteFilm(itemId : Int): Single<Int>
//
//    @Query("SELECT * FROM films WHERE id=:itemId LIMIT 1")
//    fun getFilmById(itemId : Int) : FilmsDb.FilmDbModel
//

    @Query("SELECT * FROM films WHERE id_Retrofit=:itemIdRetrofit AND isFavorite LIMIT 1")
    fun getFilmByIdRetrofit(itemIdRetrofit : Int) : FilmsDb.FilmDbModel



    @Query("SELECT * FROM films WHERE isFavorite ORDER BY id ASC")
    fun selectFavoriteFilms(): Single<List<FilmsDb.FilmDbModel>>




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteFilm(itemDbModel: FilmsDb.FilmDbModel)//: Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<FilmsDb.FilmDbModel>)

    @Query("SELECT * FROM films WHERE NOT isFavorite ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, FilmsDb.FilmDbModel>

    @Query("DELETE FROM films WHERE NOT isFavorite")
    fun clearMovies()








}