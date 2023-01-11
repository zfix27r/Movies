package ru.zfix27r.movies.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentMainBinding
import ru.zfix27r.movies.ui.SingleActivity
import ru.zfix27r.movies.ui.category.Categories
import ru.zfix27r.movies.ui.category.TOP_TYPE
import ru.zfix27r.movies.ui.common.NavigateToFilmDetailCallback
import ru.zfix27r.movies.ui.film.FILM_ID

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by viewModels<MainViewModel>()

    private val navFilm = object : NavigateToFilmDetailCallback {
        override fun toFilmDetail(id: Int) {
            findNavController().navigate(
                R.id.action_global_filmDetail, bundleOf(FILM_ID to id)
            )
        }
    }

    private val premieresAdapter = PremierAdapter(navFilm)
    private val bestAdapter = TopAdapter(navFilm)
    private val popularAdapter = TopAdapter(navFilm)
    private val awaitAdapter = TopAdapter(navFilm)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.premieresRecycler.adapter = premieresAdapter
        binding.bestRecycler.adapter = bestAdapter
        binding.popularRecycler.adapter = popularAdapter
        binding.awaitRecycler.adapter = awaitAdapter

        onObserveData()
        setListeners()
    }

    private fun onObserveData() {
        viewModel.data.observe(viewLifecycleOwner) {
            premieresAdapter.submitList(it.premieres)
            awaitAdapter.submitList(it.await)
            popularAdapter.submitList(it.popular)
            bestAdapter.submitList(it.best)
        }
    }

    private fun setListeners() {
        binding.premieresNav.setOnClickListener {

        }
        binding.awaitNav.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_filmsCategory,
                bundleOf(TOP_TYPE to Categories.TOP_AWAIT.name)
            )
        }
        binding.popularNav.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_filmsCategory,
                bundleOf(TOP_TYPE to Categories.TOP_POPULAR.name)
            )
        }
        binding.bestNav.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_filmsCategory,
                bundleOf(TOP_TYPE to Categories.TOP_BEST.name)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        hideToolbar()
    }

    override fun onPause() {
        super.onPause()
        showToolbar()
    }

    private fun hideToolbar() {
        (activity as SingleActivity).supportActionBar?.hide()
    }

    private fun showToolbar() {
        (activity as SingleActivity).supportActionBar?.show()
    }
}