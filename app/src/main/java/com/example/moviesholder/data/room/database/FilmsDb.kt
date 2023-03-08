package com.example.moviesholder.data.room.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmsDb(

    val movies: List<FilmDbModel>,
    val page: Int,
    val pages: Int,

): Parcelable {

    @IgnoredOnParcel
    val endOfPage = pages == page

    @Parcelize
    @Entity(tableName = "films")
    data class FilmDbModel(
        @PrimaryKey(autoGenerate = true)
        val id : Int = 0,
        val id_Retrofit : Int,
        val name : String,
        val poster : String,
        val rating : String,
        val description : String,
        val isFavorite : Boolean
    ): Parcelable


    @Parcelize
    @Entity(tableName = "film_remote_keys")
    data class FilmRemoteKeys(
        @PrimaryKey
        val id : Int,
        val prevKey: Int?,
        val nextKey: Int?
    ): Parcelable

}

