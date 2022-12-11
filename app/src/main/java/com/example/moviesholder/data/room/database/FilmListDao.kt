package com.example.moviesholder.data.room.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmListDao {

    @Query("SELECT * FROM films")
    fun getListFilm() : LiveData<List<FilmDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilm(itemDbModel: FilmDbModel)

    @Query("DELETE FROM films WHERE id=:itemId")
    suspend fun deleteFilm(itemId : Int)

    @Query("SELECT * FROM films WHERE id=:itemId LIMIT 1")
    suspend  fun getFilm(itemId : Int) : FilmDbModel

}