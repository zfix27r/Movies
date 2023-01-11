package ru.zfix27r.movies.ui.category

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
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
import ru.zfix27r.movies.databinding.FragmentCategoryBinding
import ru.zfix27r.movies.ui.common.RetryCallback
import ru.zfix27r.movies.ui.film.FILM_ID
import ru.zfix27r.movies.ui.common.NavigateToFilmDetailCallback

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category), RetryCallback {
    private val binding by viewBinding(FragmentCategoryBinding::bind)
    private val viewModel by viewModels<TopViewModel>()

    private val navFilm = object : NavigateToFilmDetailCallback {
        override fun toFilmDetail(id: Int) {
            findNavController().navigate(
                R.id.action_global_filmDetail, bundleOf(FILM_ID to id)
            )
        }
    }
    private val adapter = CategoryAdapter(navFilm)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recycler.adapter = adapter
        updateUI()
    }

    private fun updateUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.top.collect {
                    adapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest {
                    viewModel.isLoading.set(
                        it.refresh is LoadState.Loading || it.append is LoadState.Loading
                    )

                    if (it.refresh is LoadState.Error) {
                        (it.refresh as LoadState.Error).error.message?.let { msg ->
                            viewModel.error.set(R.string.error_no_internet)
                        }
                    }
                }

            }
        }

    }

    override fun retry(v: View) {
        viewModel.error.set(R.string.empty)
        adapter.retry()
    }
}