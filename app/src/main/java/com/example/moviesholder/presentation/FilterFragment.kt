package com.example.moviesholder.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesholder.databinding.FragmentFilterBinding


class FilterFragment : Fragment() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFilterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
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