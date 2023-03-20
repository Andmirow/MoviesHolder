package com.example.moviesholder.presentation

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesholder.data.MapperFilm
import com.example.moviesholder.databinding.FragmentListFilmBinding
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmApp
import com.example.moviesholder.presentation.recycler_view_tools.FilmPagingDataAdapter
import com.example.moviesholder.presentation.recycler_view_tools.LoadingGridStateAdapter
import com.example.moviesholder.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ListFilmFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding : FragmentListFilmBinding
    private lateinit var fragmentControl : FragmentControl
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var mAdapter: FilmPagingDataAdapter


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
        mAdapter = FilmPagingDataAdapter(this::openFilmCard,this::deleteFilm)

        val view = binding.root

        Log.i("MyResult","onCreateView")





        return view
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
        setRecyclerView(view)
        setListener()
    }


    private fun setListener(){
        binding.filterButton.setOnClickListener {
            fragmentControl.openNewFragment(FilterFragment.newInstance())
        }
        binding.switchSave.setOnCheckedChangeListener{ _, isChecked ->
            viewModel.isPreserved = isChecked

            scope.launch {
                viewModel.getFilms((activity?.application as FilmApp).filmApi).collectLatest {
                    mAdapter.submitData(it.map { it -> MapperFilm.mapFilmDbModelToFilm(it) })
                }
            }
            //mAdapter.refresh()
        }

        binding.refresh.setOnClickListener {
            mAdapter.refresh()
        }
    }

    private fun setRecyclerView(view : View){
        val recycler = binding.rvFilmList
        recycler.layoutManager = GridLayoutManager(view.context, 2)
        recycler.adapter = mAdapter
        recycler.adapter = mAdapter.withLoadStateFooter(
            footer = LoadingGridStateAdapter()
        )
        mAdapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                AlertDialog.Builder(view.context)
                    .setTitle("error2")
                    .setMessage(it.error.localizedMessage)
                    .setNegativeButton("cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("retry") { _, _ ->
                        mAdapter.retry()
                    }
                    .show()
            }
        }

        val dividerItemDecorationVERTICAL = DividerItemDecoration(recycler.context, GridLayoutManager.VERTICAL)
        val dividerItemDecorationHORIZONTAL = DividerItemDecoration(recycler.context, GridLayoutManager.HORIZONTAL)
        recycler.addItemDecoration(dividerItemDecorationVERTICAL)
        recycler.addItemDecoration(dividerItemDecorationHORIZONTAL)

        scope.launch {
            viewModel.getFilms((activity?.application as FilmApp).filmApi).collectLatest {
                mAdapter.submitData(it.map { it -> MapperFilm.mapFilmDbModelToFilm(it) })
            }
        }
    }

    private fun openFilmCard(film : Film){
        fragmentControl.openNewFragment(FilmInfoFragment.newInstance(film))
    }

    private fun deleteFilm(film : Film){
        scope.launch {
            viewModel.deleteFilm(film, (activity?.application as FilmApp).filmApi)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
    }
}