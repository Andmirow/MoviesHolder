package com.example.moviesholder.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesholder.R
import com.example.moviesholder.databinding.ActivityMainBinding
import com.example.moviesholder.domain.example1.Activity
import kotlinx.android.synthetic.main.film_info.view.*


class MainActivity() : AppCompatActivity(), FragmentСontrol {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(ListFilmFragment.newInstance())

        binding.filterButton.setOnClickListener {
            openFragment(FilterFragment())
            //Toast.makeText(this, "Пока не реализовал", Toast.LENGTH_LONG).show()
        }

        val activity = Activity()
        Toast.makeText(this,activity.keyboard.toString(),Toast.LENGTH_LONG).show()


    }


    fun openFragment(fragment : Fragment){
        //supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun openNewFragment(fragment : Fragment) {
        openFragment(fragment)
    }


    override fun closeFragment() {
        supportFragmentManager.popBackStack()
    }


}