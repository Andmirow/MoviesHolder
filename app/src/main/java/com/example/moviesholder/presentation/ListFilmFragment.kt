package com.example.moviesholder.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesholder.data.MapperFilm
import com.example.moviesholder.databinding.FragmentListFilmBinding
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmApp
import com.example.moviesholder.domain.MovieFilter
import com.example.moviesholder.presentation.recycler_view_tools.FilmAdapter
import com.example.moviesholder.presentation.recycler_view_tools.FilmPagingDataAdapter
import com.example.moviesholder.presentation.recycler_view_tools.LoadingGridStateAdapter
import io.reactivex.disposables.CompositeDisposable


class ListFilmFragment : Fragment() {

    //private val viewModel: RxViewModel by activityViewModels()
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding : FragmentListFilmBinding
    private lateinit var fragmentControl : FragmentControl

    private lateinit var mAdapter: FilmPagingDataAdapter

    private val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel.fetchList((activity?.application as FilmApp).filmApi)
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
        mAdapter = FilmPagingDataAdapter(this::openFilmCard,this::deleteFilm)

        val view = binding.root

        setRecyclerView(view)





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
        //setRecyclerView()
        setListener()
    }

    @SuppressLint("SetTextI18n")
    private fun setListener(){
        binding.filterButton.setOnClickListener {
            fragmentControl.openNewFragment(FilterFragment.newInstance())
        }
        binding.switchSave.setOnCheckedChangeListener{ _, isChecked ->
            MovieFilter.isPreserved = isChecked
            //viewModel.fetchList((activity?.application as FilmApp).filmApi)
        }
        binding.buttonMax.setOnClickListener {
           MovieFilter.naxtPage()
            //viewModel.fetchList((activity?.application as FilmApp).filmApi)
            val page = MovieFilter.page.toInt()
            binding.buttonMax.text = ">"+ (page+1).toString()
            binding.buttonMun.text = "<"+(page-1).toString()
        }
        binding.buttonMun.setOnClickListener {
            MovieFilter.earlyPage()
            //viewModel.fetchList((activity?.application as FilmApp).filmApi)
            val page = MovieFilter.page.toInt()
            binding.buttonMax.text =">"+ (page+1).toString()
            binding.buttonMun.text = "<"+(page-1).toString()
        }
    }

    private fun setRecyclerView(view : View){
        val recycler = binding.rvFilmList
        recycler.layoutManager = GridLayoutManager(view.context, 4)
        //val adapter = FilmAdapter(this::openFilmCard,this::deleteFilm)
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
                    .setTitle("error")
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



        mDisposable.add(viewModel.getMovies().subscribe {
            mAdapter.submitData(lifecycle, it.map { it -> MapperFilm.mapFilmDbModelToFilm(it) })
        })



//        viewModel.selected.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }
    }

    private fun openFilmCard(film : Film){
        fragmentControl.openNewFragment(FilmInfoFragment.newInstance(film))
    }

    private fun deleteFilm(film : Film){
        //viewModel.deleteFilm(film)
    }


    override fun onDestroyView() {
        mDisposable.dispose()
        super.onDestroyView()
    }
}