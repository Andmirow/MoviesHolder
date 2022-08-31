package com.example.moviesholder.data.rfrxok

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesholder.R
import com.example.moviesholder.data.rfrxok.data.ViewModelQestion

class QestionListFragment : Fragment(R.layout.fragment_sender) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("MyResult", "start onViewCreated")
        super.onViewCreated(view, savedInstanceState)


        val qestlistViewModel =  ViewModelProvider(this).get(ViewModelQestion::class.java)
        Log.i("MyResult", "create qestlistViewModel")

        val tet = qestlistViewModel.fetchList((activity?.application as FilmApp).filmApi)



        Log.i("MyResult", "finish onViewCreated")
    }
}