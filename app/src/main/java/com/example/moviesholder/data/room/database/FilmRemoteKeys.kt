package com.example.moviesholder.data.room.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "film_remote_keys")
data class FilmRemoteKeys(

    @PrimaryKey
    val id : Int,
    val prevKey: Int?,
    val nextKey: Int?

)
