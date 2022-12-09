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
import ru.zfix27r.movies.domain.common.ResponseThrow
import ru.zfix27r.movies.domain.common.ResponseType
import ru.zfix27r.movies.domain.model.TopResModel

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by viewModels<MainViewModel>()
    private val adapter = MovieAdapter(onListenAction())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recycler.adapter = adapter

        observeMovies()
        observeResponse()
    }

    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    private fun observeResponse() {
        viewModel.response.observe(viewLifecycleOwner) {
            if (it is ResponseThrow) {
                when (it.type) {
                    ResponseType.NO_INTERNET -> showSnack(getString(it.type.msg))
                }
            } else throw it
        }
    }

    private fun showSnack(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
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