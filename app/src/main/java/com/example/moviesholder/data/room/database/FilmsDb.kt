package com.example.moviesholder.data.room.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


data class FilmsDb(

    val movies: List<FilmDbModel>,
    val page: Int,
    val pages: Int,

) {

    @IgnoredOnParcel
    val endOfPage = pages == page


    @Entity(tableName = "films")
    data class FilmDbModel(
        @PrimaryKey(autoGenerate = true)
        val id : Int = 0,
        val id_Retrofit : Int,
        val name : String?,
        val poster : String?,
        val rating : String?,
        val description : String?,
        var isFavorite : Boolean
    )



    @Entity(tableName = "film_remote_keys")
    data class FilmRemoteKeys(
        @PrimaryKey
        val id : Int,
        val prevKey: Int?,
        val nextKey: Int?
    )

}

