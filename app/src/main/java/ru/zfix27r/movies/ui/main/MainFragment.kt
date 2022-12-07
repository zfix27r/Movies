package ru.zfix27r.movies.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.zfix27r.movies.R
import ru.zfix27r.movies.data.FilmTopResponse
import ru.zfix27r.movies.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by viewModels<MainViewModel>()
    private val adapter = MovieAdapter(onListenAction())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter

        observeNotes()
    }

    private fun observeNotes() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            println(it)
        }
    }

    private fun onListenAction(): MovieActionListener {
        return object : MovieActionListener {
            override fun onViewDetail(film: FilmTopResponse.Film) {

            }
        }
    }
}