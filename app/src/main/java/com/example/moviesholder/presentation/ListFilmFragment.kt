package com.example.moviesholder.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesholder.databinding.FragmentListFilmBinding
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmApp
import com.example.moviesholder.domain.MovieFilter
import com.example.moviesholder.presentation.recycler_view_tools.FilmAdapter


class ListFilmFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding : FragmentListFilmBinding
    private lateinit var fragmentControl : FragmentControl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchList((activity?.application as FilmApp).filmApi)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentControl) {
            fragmentControl = context
        } else {
            throw RuntimeException("Activity must implement FragmentControl")
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListFilmBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ListFilmFragment().apply {
                arguments = Bundle()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setListener()
    }

    @SuppressLint("SetTextI18n")
    private fun setListener(){
        binding.filterButton.setOnClickListener {
            fragmentControl.openNewFragment(FilterFragment.newInstance())
        }
        binding.switchSave.setOnCheckedChangeListener{ _, isChecked ->
            MovieFilter.isPreserved = isChecked
            viewModel.fetchList((activity?.application as FilmApp).filmApi)
        }
        binding.buttonMax.setOnClickListener {
           MovieFilter.naxtPage()
            viewModel.fetchList((activity?.application as FilmApp).filmApi)
            val page = MovieFilter.page.toInt()
            binding.buttonMax.text = ">"+ (page+1).toString()
            binding.buttonMun.text = "<"+(page-1).toString()
        }
        binding.buttonMun.setOnClickListener {
            MovieFilter.earlyPage()
            viewModel.fetchList((activity?.application as FilmApp).filmApi)
            val page = MovieFilter.page.toInt()
            binding.buttonMax.text =">"+ (page+1).toString()
            binding.buttonMun.text = "<"+(page-1).toString()
        }
    }

    private fun setRecyclerView(){
        val recycler = binding.rvFilmList
        recycler.layoutManager = GridLayoutManager(activity?.applicationContext, 4)
        val adapter = FilmAdapter(this::openFilmCard,this::deleteFilm)
        recycler.adapter = adapter
        val dividerItemDecorationVERTICAL = DividerItemDecoration(recycler.context, GridLayoutManager.VERTICAL)
        val dividerItemDecorationHORIZONTAL = DividerItemDecoration(recycler.context, GridLayoutManager.HORIZONTAL)
        recycler.addItemDecoration(dividerItemDecorationVERTICAL)
        recycler.addItemDecoration(dividerItemDecorationHORIZONTAL)
        viewModel.selected.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun openFilmCard(film : Film){
        fragmentControl.openNewFragment(FilmInfoFragment.newInstance(film))
    }

    private fun deleteFilm(film : Film){
        viewModel.deleteFilm(film)
    }


}