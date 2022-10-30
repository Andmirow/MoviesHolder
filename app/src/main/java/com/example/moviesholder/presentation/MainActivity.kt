package com.example.moviesholder.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesholder.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MyResult", "start activity")
//        val jsonObject: JSONObject? = NetworkUtils.getJSONFromNetwork()
//        Log.i("MyResult", jsonObject.toString())
//        val movies: ArrayList<FilmModel> = JSONUtils.getMoviesFromJSON(jsonObject)
//        val builder = StringBuilder()
//        for (movie in movies) {
//            builder.append(movie.name).append("\n")
//        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, QestionListFragment::class.java, null)
            .commit()


//        supportFragmentManager.beginTransaction()
//            .replace(R.id.frag, QestionListFragment()).commit()

        Log.i("MyResult", "finish")
    }
}