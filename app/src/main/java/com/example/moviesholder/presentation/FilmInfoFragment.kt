package com.example.moviesholder.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moviesholder.R
import com.example.moviesholder.data.retrofit.film_model.Doc
import com.example.moviesholder.databinding.FilmInfoBinding
import com.example.moviesholder.domain.Film


private const val ARG_FILM = "param1"



class FilmInfoFragment : Fragment() {

    lateinit var film: Film
    lateinit var binding : FilmInfoBinding
    private var howToCloseFragment : FragmentСontrol? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        if (args.containsKey(ARG_FILM)) {
            film = args.getParcelable(ARG_FILM)!!
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentСontrol) {
            howToCloseFragment = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =FilmInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRating.text = film.rating
        binding.tvDescription.text = film.description

        Glide.with(binding.ivPoster.context)
            .load(film.poster)
            .centerCrop()
            .into(binding.ivPoster)


        binding.save.setOnClickListener {
            Toast.makeText(activity, "Пока не реализовал", Toast.LENGTH_LONG).show()
            howToCloseFragment?.closeFragment()
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(film: Film) =
            FilmInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_FILM, film)
                }
            }
    }
}