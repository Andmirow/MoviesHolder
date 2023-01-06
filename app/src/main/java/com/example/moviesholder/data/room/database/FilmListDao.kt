package com.example.moviesholder.data.room.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmListDao {

    @Query("SELECT * FROM films")
    fun getListFilm() : List<FilmDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFilm(itemDbModel: FilmDbModel)

    @Query("DELETE FROM films WHERE id=:itemId")
    fun deleteFilm(itemId : Int)

    @Query("SELECT * FROM films WHERE id=:itemId LIMIT 1")
    fun getFilmById(itemId : Int) : FilmDbModel

    @Query("SELECT * FROM films WHERE id_Retrofit=:itemIdRetrofit LIMIT 1")
    fun getFilmByIdRetrofit(itemIdRetrofit : Int) : FilmDbModel



}