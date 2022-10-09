package com.example.moviesholder.data.rfrxok

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesholder.R
import com.example.moviesholder.data.rfrxok.data.ViewModelQestion

class QestionListFragment : Fragment(R.layout.fragment_sender) {

    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("MyResult", "start onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.senderButton).setOnClickListener {
            val we = ""
        }




        val qestlistViewModel =  ViewModelProvider(this).get(ViewModelQestion::class.java)
        Log.i("MyResult", "create qestlistViewModel")

        val tet = qestlistViewModel.fetchList((activity?.application as FilmApp).filmApi)

        Log.i("MyResult", "finish onViewCreated")





        textView = view.findViewById(R.id.et_message)
        qestlistViewModel.selected.observe(viewLifecycleOwner, Observer { message ->
            textView.text = message.toString()
        })
    }





}