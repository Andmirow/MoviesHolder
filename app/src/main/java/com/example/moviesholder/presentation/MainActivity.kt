package com.example.moviesholder.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.moviesholder.R
import com.example.moviesholder.databinding.ActivityMainBinding
import com.example.moviesholder.domain.FilmApp
import com.example.moviesholder.presentation.recycler_view_tools.FilmAdapter


class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.fetchList((this.application as FilmApp).filmApi)
        setRecyclerView()



//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.fragment_container_view, QestionListFragment::class.java, null)
//            .commit()
//
//
//        Log.i("MyResult", "finish")
    }

    fun setRecyclerView(){
        val recycler = binding.rvFilmList
        val adapter = FilmAdapter()
        recycler.adapter = adapter

        viewModel.selected.observe(this) {
            adapter.submitList(it.docs)
        }
    }


}