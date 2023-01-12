package com.example.moviesholder.presentation

import androidx.fragment.app.Fragment

interface FragmentControl {

    fun closeFragment()
    fun openNewFragment(fragment : Fragment)


}