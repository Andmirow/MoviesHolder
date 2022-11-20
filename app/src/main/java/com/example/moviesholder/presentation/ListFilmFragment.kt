package com.example.moviesholder.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesholder.databinding.FragmentListFilmBinding
import com.example.moviesholder.data.retrofit.FilmApp
import com.example.moviesholder.presentation.recycler_view_tools.FilmAdapter





class ListFilmFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    lateinit var binding : FragmentListFilmBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        viewModel.fetchList((activity?.application as FilmApp).filmApi)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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


    fun setRecyclerView(){
        val recycler = binding.rvFilmList
        recycler.layoutManager = GridLayoutManager(activity?.applicationContext, 4)
        val adapter = FilmAdapter()

        recycler.adapter = adapter
        val dividerItemDecorationVERTICAL = DividerItemDecoration(recycler.context, GridLayoutManager.VERTICAL)
        val dividerItemDecorationHORIZONTAL = DividerItemDecoration(recycler.context, GridLayoutManager.HORIZONTAL)
        recycler.addItemDecoration(dividerItemDecorationVERTICAL)
        recycler.addItemDecoration(dividerItemDecorationHORIZONTAL)

        viewModel.selected.observe(viewLifecycleOwner) {
            adapter.submitList(it.docs)
        }
    }



}