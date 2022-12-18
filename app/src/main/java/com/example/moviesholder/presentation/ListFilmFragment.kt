package com.example.moviesholder.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.di.FilmApp
import com.example.moviesholder.databinding.FragmentListFilmBinding
import com.example.moviesholder.di.DaggerFilmComponent
import com.example.moviesholder.domain.Film
import com.example.moviesholder.presentation.recycler_view_tools.FilmAdapter
import javax.inject.Inject


class ListFilmFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding : FragmentListFilmBinding
    private lateinit var fragmentСontrol : FragmentСontrol

    @Inject
    lateinit var filmApi : FilmApi

//    init {
//        DaggerFilmComponent.create().inject(this)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFilmComponent.create().inject(this)
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.fetchList((activity?.application as FilmApp).filmApi)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentСontrol) {
            fragmentСontrol = context
        } else {
            throw RuntimeException("Activity must implement FragmentСontrol")
        }
        Log.i("MyResult","onAttach")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListFilmBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        private const val FILTER_PARAM = "filter"


        @JvmStatic
        fun newInstance() =
            ListFilmFragment().apply {
                arguments = Bundle()
            }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }







    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i("testus","onViewStateRestored")
        viewModel.fetchList((activity?.application as FilmApp).filmApi)
    }

    override fun onStart() {
        super.onStart()
        Log.i("testus","onStart")

    }
    override fun onPause() {
        super.onPause()
        Log.i("testus","onResume")

    }







    fun setRecyclerView(){
        val recycler = binding.rvFilmList
        recycler.layoutManager = GridLayoutManager(activity?.applicationContext, 4)
        val adapter = FilmAdapter(this::openFilmCard)
        recycler.adapter = adapter
        val dividerItemDecorationVERTICAL = DividerItemDecoration(recycler.context, GridLayoutManager.VERTICAL)
        val dividerItemDecorationHORIZONTAL = DividerItemDecoration(recycler.context, GridLayoutManager.HORIZONTAL)
        recycler.addItemDecoration(dividerItemDecorationVERTICAL)
        recycler.addItemDecoration(dividerItemDecorationHORIZONTAL)

        viewModel.selected.observe(viewLifecycleOwner) {
            adapter.submitList(it.docs)
        }
    }


    fun openFilmCard(film : Film){
        Toast.makeText(binding.root.context, "click", Toast.LENGTH_LONG).show()
        fragmentСontrol.openNewFragment(FilmInfoFragment.newInstance(film))
    }



}