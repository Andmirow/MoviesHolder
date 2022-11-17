package com.example.moviesholder.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
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
        recycler.layoutManager = GridLayoutManager(this, 4)
        val adapter = FilmAdapter()

        recycler.adapter = adapter
        val dividerItemDecorationVERTICAL = DividerItemDecoration(recycler.context, GridLayoutManager.VERTICAL)
        val dividerItemDecorationHORIZONTAL = DividerItemDecoration(recycler.context, GridLayoutManager.HORIZONTAL)
        recycler.addItemDecoration(dividerItemDecorationVERTICAL)
        recycler.addItemDecoration(dividerItemDecorationHORIZONTAL)

//        viewModel.selected.observe(this){
//            adapter.reloadList(it.docs)
//        }

        viewModel.selected.observe(this) {
            adapter.submitList(it.docs)
        }
    }





}