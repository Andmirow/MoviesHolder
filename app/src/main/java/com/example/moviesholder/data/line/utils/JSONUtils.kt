package com.example.moviesholder.data.line.utils

import android.util.Log
import com.example.moviesholder.data.film_object.movie.film_model.FilmModel
import com.example.moviesholder.data.film_object.movie.film_model.Poster
import org.json.JSONException
import org.json.JSONObject

object JSONUtils {

    private const val KEY_DOCS = "docs"
    private const val KEY_ID = "id"
    private const val KEY_NAME = "name"
    private const val KEY_POSTER = "poster"
    private const val KEY_INNER_ID = "_id"
    private const val KEY_PREVIEW_URL = "previewUrl"
    private const val KEY_URL = "url"
    private const val KEY_DESCRIPTION = "description"
    private const val KEY_YEAR = "year"
    private const val KEY_ALTERNATIVE_NAME = "alternativeName"

    private const val KEY_VOTE_AVERAGE = "vote_average"
    private const val KEY_RELEASE_DATE = "release_date"

    fun getMoviesFromJSON(jsonObject: JSONObject?): ArrayList<FilmModel> {
        Log.i("MyResult", "getMoviesFromJSON")
        val result: ArrayList<FilmModel> = ArrayList()
        jsonObject?.let {
            val jsonDocs = jsonObject.getJSONArray(KEY_DOCS)
            for (i in 0 until jsonDocs.length()) {
                val objectMovie = jsonDocs.getJSONObject(i)
                val id = objectMovie.getInt(KEY_ID)
                val name = objectMovie.getString(KEY_NAME)

                val poster = objectMovie.getJSONObject(KEY_POSTER)
                val posterId = poster.getString(KEY_INNER_ID)
                val posterPreviewPath = poster.getString(KEY_PREVIEW_URL)
                val posterPath = poster.getString(KEY_URL)

                val description = objectMovie.getString(KEY_DESCRIPTION)
                val year = objectMovie.getInt(KEY_YEAR)
                val alternativeName = objectMovie.getString(KEY_ALTERNATIVE_NAME)



                val posterObj = Poster(posterId,posterPath,posterPreviewPath)
                val movie = FilmModel(null,posterObj,null,
                null,id,alternativeName,description,null,
                    null,name,null,null,null,year)

                result.add(movie)
            }
        }
        Log.i("MyResult", result.size.toString())
        return result
    }

}