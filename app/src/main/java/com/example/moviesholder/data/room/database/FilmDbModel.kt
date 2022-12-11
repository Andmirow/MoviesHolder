package com.example.moviesholder.data.room.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "films")
data class FilmDbModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val poster : String,
    val rating : String,
    val description : String
)