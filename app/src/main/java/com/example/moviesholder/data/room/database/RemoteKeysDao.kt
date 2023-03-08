package com.example.moviesholder.data.room.database

import androidx.room.*


@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<FilmsDb.FilmRemoteKeys>)

    @Query("SELECT * FROM film_remote_keys WHERE id = :movieId")
    fun remoteKeysByMovieId(movieId: Int): FilmsDb.FilmRemoteKeys?

    @Query("DELETE FROM film_remote_keys")
    fun clearRemoteKeys()


}