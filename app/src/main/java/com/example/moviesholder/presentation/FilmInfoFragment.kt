package com.example.moviesholder.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.moviesholder.databinding.FilmInfoBinding
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


private const val ARG_FILM = "param1"



class FilmInfoFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var film: Film
    private lateinit var binding : FilmInfoBinding
    private var howToCloseFragment : FragmentControl? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        if (args.containsKey(ARG_FILM)) {
            film = args.getParcelable(ARG_FILM)!!
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentControl) {
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
            .fitCenter()
            .into(binding.ivPoster)


        binding.save.setOnClickListener {
            scope.launch{
                viewModel.saveFavoriteFilm(film,(activity?.application as FilmApp).filmApi)
            }
            howToCloseFragment?.closeFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
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