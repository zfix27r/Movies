package ru.zfix27r.movies.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentMainBinding
import ru.zfix27r.movies.domain.model.TopResModel
import ru.zfix27r.movies.ui.snack

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by viewModels<MainViewModel>()
    private val adapter = MovieAdapter(onListenAction())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recycler.adapter = adapter

        observeLoadState()
        observeTopData()
    }


    private fun observeLoadState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest {
                    binding.progressBar.isVisible =
                        it.refresh is LoadState.Loading || it.append is LoadState.Loading || false
                }
            }
        }
    }

    private fun observeTopData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.top.collectLatest { adapter.submitData(it) }
            }
        }
    }

    private fun onListenAction(): MovieActionListener {
        return object : MovieActionListener {
            override fun onViewDetail(topResModel: TopResModel) {
                findNavController().navigate(
                    R.id.action_main_to_movieDetail,
                    bundleOf(MOVIE_ID to topResModel.id)
                )
            }
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"
    }
}