package com.example.moviesholder.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesholder.R
import com.example.moviesholder.databinding.ActivityMainBinding


class MainActivity() : AppCompatActivity(), FragmentСontrol {

    lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.popBackStack()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(ListFilmFragment.newInstance())

        binding.filterButton.setOnClickListener {
            openFragment(FilterFragment())
            //Toast.makeText(this, "Пока не реализовал", Toast.LENGTH_LONG).show()
        }

//        val activity = Activity()
//        Toast.makeText(this,activity.keyboard.toString(),Toast.LENGTH_LONG).show()


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