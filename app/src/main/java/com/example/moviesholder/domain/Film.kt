package com.example.moviesholder.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Film(

    val name: String,
    val poster: String?,
    val rating: String,
    val description: String

) : Parcelable