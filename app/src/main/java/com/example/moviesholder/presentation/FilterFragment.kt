package com.example.moviesholder.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moviesholder.di.FilmApp
import com.example.moviesholder.databinding.FragmentFilterBinding
import com.example.moviesholder.domain.MovieFilter


class FilterFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding : FragmentFilterBinding
    lateinit var fragmentСontrol : FragmentСontrol


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
        binding = FragmentFilterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.editText?.setText(MovieFilter.search)
        binding.field.editText?.setText(MovieFilter.field)
        binding.sortedField.editText?.setText(MovieFilter.sortedField)
        binding.sortedType.editText?.setText(MovieFilter.sortedType)
        binding.save.setOnClickListener {
            MovieFilter.search = binding.search.editText?.text.toString()
            MovieFilter.field = binding.field.editText?.text.toString()
            MovieFilter.sortedField = binding.sortedField.editText?.text.toString()
            MovieFilter.sortedType = binding.sortedType.editText?.text.toString()
            viewModel.fetchList((activity?.application as FilmApp).filmApi)
            fragmentСontrol.closeFragment()
        }

    }




    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FilterFragment().apply {
                arguments = Bundle()
            }
    }
}