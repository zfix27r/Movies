package ru.zfix27r.movies.ui.film

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentMovieDetailBinding

const val FILM_ID = "film_id"

@AndroidEntryPoint
class FilmDetailFragment : Fragment(R.layout.fragment_movie_detail) {
    private val binding by viewBinding(FragmentMovieDetailBinding::bind)
    private val viewModel by viewModels<MovieDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        observeMovie()
    }

    private fun observeMovie() {
        viewModel.movie.observe(viewLifecycleOwner) {
            Glide.with(requireContext()).load(it.posterUrl).into(binding.poster)
        }
    }
}