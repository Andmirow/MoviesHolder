package com.example.moviesholder.data.room.database

import androidx.room.*


@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<FilmsDb.FilmRemoteKeys>)

    @Query("SELECT * FROM film_remote_keys WHERE id = :movieId")
    suspend fun remoteKeysByMovieId(movieId: Int): FilmsDb.FilmRemoteKeys?

    @Query("DELETE FROM film_remote_keys")
    suspend fun clearRemoteKeys()


}