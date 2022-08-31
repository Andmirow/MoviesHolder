package com.example.moviesholder.data.line.utils

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.ExecutionException

object NetworkUtils {

    private const val BASE_URL = "https://api.kinopoisk.dev/movie"

    private const val PARAMS_API_KEY = "token"
    private const val PARAMS_SEARCH = "search"
    private const val PARAMS_FIELD = "field"
    private const val PARAMS_SORT_FIELD = "sortField"
    private const val PARAMS_SORT_TYPE = "sortType"
    private const val PARAMS_PAGE = "page"


    private const val API_KEY = "54PEKD1-QV741T0-NHKSR4H-2JXE7A4"
    private const val SEARCH_VALUE = "1-10"
    private const val FIELD_VALUE = "rating.kp"
    private const val SORT_TYPE_VALUE = "-1"


    private fun buildURL(): URL? {
        Log.i("MyResult", "buildURL")
        var result: URL? = null
        val uri = Uri.parse(BASE_URL).buildUpon()
            .appendQueryParameter(PARAMS_API_KEY, API_KEY)
            .appendQueryParameter(PARAMS_SEARCH, SEARCH_VALUE)
            .appendQueryParameter(PARAMS_FIELD, FIELD_VALUE)
            .appendQueryParameter(PARAMS_SORT_FIELD, FIELD_VALUE)
            .appendQueryParameter(PARAMS_SORT_TYPE, SORT_TYPE_VALUE)
            .appendQueryParameter(PARAMS_PAGE, "1")
            .appendQueryParameter("limit", "1")
            .build()
        try {
            result = URL(uri.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        Log.i("MyResult", "finish buildURL")
        return result
    }

    fun getJSONFromNetwork(): JSONObject? {
        Log.i("MyResult", "getJSONFromNetwork")
        var result: JSONObject? = null
        val url = buildURL()
        try {
            val result = JSONLoadTask()
            //var url = "https://api.openweathermap.org/data/2.5/weather?q=${plaseView.text.toString()}&appid=5e07acfb0dfbea2aabc932dad0b2f40f&lang=ru&units=metric"
            result.execute(url)



            //result = JSONLoadTask().execute(url).get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("MyResult", "finish getJSONFromNetwork")
        return result
    }

    private class JSONLoadTask : AsyncTask<URL?, Void?, JSONObject?>() {

        override fun doInBackground(vararg p0: URL?): JSONObject? {
            Log.i("MyResult", "doInBackground")
            var result: JSONObject? = null
            if (p0 == null || p0.size == 0) {
                return result
            }
            Log.i("where is timeuot", "1")
            var connection: HttpURLConnection? = null
            Log.i("where is timeuot", "2")
            try {
                Log.i("where is timeuot", "3")
                connection = p0[0]?.openConnection() as HttpURLConnection
                Log.i("where is timeuot", "4")
                val inputStream = connection.inputStream
                Log.i("where is timeuot", "5")
                val inputStreamReader = InputStreamReader(inputStream)
                Log.i("where is timeuot", "6")
                val reader = BufferedReader(inputStreamReader)
                Log.i("where is timeuot", "7")
                val builder = StringBuilder()
                Log.i("where is timeuot", "8")
                var line = reader.readLine()
                Log.i("where is timeuot", "9")
                while (line != null) {
                    builder.append(line)
                    line = reader.readLine()
                }
                Log.i("where is timeuot", "10")
                result = JSONObject(builder.toString())
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
            Log.i("MyResult", "finish doInBackground")
            return result
        }
    }
}