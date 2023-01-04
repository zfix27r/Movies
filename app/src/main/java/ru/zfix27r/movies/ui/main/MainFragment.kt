package ru.zfix27r.movies.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentMainBinding
import ru.zfix27r.movies.ui.detail.FILM_ID

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by viewModels<MainViewModel>()

    private val navFilm = object : NavFilmListener {
        override fun navigate(id: Int) {
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

        listenRetry()
        observeData()
    }

    private fun listenRetry() {
/*        binding.retry.setOnClickListener {
            adapter.retry()
        }*/
    }


/*    private fun observeLoadState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest {
                    binding.progressBar.isVisible =
                        it.refresh is LoadState.Loading || it.append is LoadState.Loading
                    binding.responseContainer.isVisible =
                        it.refresh is LoadState.Error

                    if (it.refresh is LoadState.Error) {
                        (it.refresh as LoadState.Error).error.message?.let { msg ->
                            showSnack(msg)
                        }
                    }
                }
            }
        }
    }*/

    private fun observeData() {
        viewModel.premieres.observe(viewLifecycleOwner) { premieresAdapter.submitList(it) }
        viewModel.await.observe(viewLifecycleOwner) { awaitAdapter.submitList(it) }
        viewModel.popular.observe(viewLifecycleOwner) { popularAdapter.submitList(it) }
        viewModel.best.observe(viewLifecycleOwner) { bestAdapter.submitList(it) }
/*
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.premieres.collectLatest {
                    premieresAdapter.submitData(it)
                }
                viewModel.best.collectLatest {
                    bestAdapter.submitList(it)
                }
                viewModel.popular.collectLatest {
                    popularAdapter.submitList(it)
                }
                viewModel.await.collectLatest {
                    awaitAdapter.submitList(it)
                }
            }
        }
*/
    }

/*    private fun onListenAction(): MovieActionListener {
        return object : MovieActionListener {
            override fun onViewDetail(topResModel: FilmCompactResModel) {
                findNavController().navigate(
                    R.id.action_main_to_movieDetail,
                    bundleOf(MOVIE_ID to topResModel.id)
                )
            }
        }
    }*/

    private fun showSnack(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }
}