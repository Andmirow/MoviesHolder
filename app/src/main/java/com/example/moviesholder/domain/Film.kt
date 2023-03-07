package com.example.moviesholder.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Film(

    val idRetrofit : Int,
    val idRoom : Int,
    val name: String?,
    val poster: String?,
    val rating: String?,
    val description: String?,
    val page : Int,
    val isFavorite : Boolean

) : Parcelable
