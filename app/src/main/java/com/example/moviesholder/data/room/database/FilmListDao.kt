package com.example.moviesholder.data.room.database

import androidx.paging.PagingSource
import androidx.room.*
import io.reactivex.Single

@Dao
interface FilmListDao {

    @Query("SELECT * FROM films")
    fun getListFilm() : Single<List<FilmDbModel>>

    @Query("SELECT * FROM films WHERE isFavorite = 'true' ")
    fun getListFavoriteFilm() : Single<List<FilmDbModel>>


    @Query("SELECT * FROM films WHERE :page IS NULL OR page = :page")
    fun getPagingSource(
        page: Int?
    ): PagingSource<Int, FilmDbModel>


    @Query("DELETE FROM films WHERE :page IS NULL OR page = :page")
    fun clear(page: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFilm(itemDbModel: FilmDbModel): Single<Unit>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(listOf: List<FilmDbModel>)

    fun save(itemDbModel: FilmDbModel) {
        save(listOf(itemDbModel))
    }


    @Transaction
    fun refresh(page: Int?, itemDbModel: List<FilmDbModel>) {
        clear(page)
        save(itemDbModel)
    }





    @Query("DELETE FROM films WHERE id=:itemId")
    fun deleteFilm(itemId : Int): Single<Int>

    @Query("SELECT * FROM films WHERE id=:itemId LIMIT 1")
    fun getFilmById(itemId : Int) : FilmDbModel

    @Query("SELECT * FROM films WHERE id_Retrofit=:itemIdRetrofit LIMIT 1")
    fun getFilmByIdRetrofit(itemIdRetrofit : Int) : FilmDbModel





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<FilmDbModel>)

    @Query("SELECT * FROM films ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, FilmDbModel>

    @Query("DELETE FROM films")
    fun clearMovies()








}