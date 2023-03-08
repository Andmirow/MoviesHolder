package com.example.moviesholder.data.room.database

import androidx.paging.PagingSource
import androidx.room.*
import io.reactivex.Single

@Dao
interface FilmListDao {

//    @Query("SELECT * FROM films")
//    fun getListFilm() : Single<List<FilmsDb.FilmDbModel>>
//
//    @Query("SELECT * FROM films WHERE isFavorite = 'true' ")
//    fun getListFavoriteFilm() : Single<List<FilmsDb.FilmDbModel>>
//
//
//    @Query("SELECT * FROM films")
//    fun getPagingSource(
//        page: Int?
//    ): PagingSource<Int, FilmsDb.FilmDbModel>
//
//
//    @Query("DELETE FROM films WHERE :page IS NULL OR page = :page")
//    fun clear(page: Int?)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun addFilm(itemDbModel: FilmsDb.FilmDbModel): Single<Unit>
//
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun save(listOf: List<FilmsDb.FilmDbModel>)
//
//    fun save(itemDbModel: FilmsDb.FilmDbModel) {
//        save(listOf(itemDbModel))
//    }
//
//
//    @Transaction
//    fun refresh(page: Int?, itemDbModel: List<FilmsDb.FilmDbModel>) {
//        clear(page)
//        save(itemDbModel)
//    }
//
//
//
//
//
//    @Query("DELETE FROM films WHERE id=:itemId")
//    fun deleteFilm(itemId : Int): Single<Int>
//
//    @Query("SELECT * FROM films WHERE id=:itemId LIMIT 1")
//    fun getFilmById(itemId : Int) : FilmsDb.FilmDbModel
//
//    @Query("SELECT * FROM films WHERE id_Retrofit=:itemIdRetrofit LIMIT 1")
//    fun getFilmByIdRetrofit(itemIdRetrofit : Int) : FilmsDb.FilmDbModel





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<FilmsDb.FilmDbModel>)

    @Query("SELECT * FROM films ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, FilmsDb.FilmDbModel>

    @Query("DELETE FROM films")
    fun clearMovies()








}