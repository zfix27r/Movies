package ru.zfix27r.movies.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentMainBinding
import ru.zfix27r.movies.domain.model.TopResModel

const val SERVICE_INTENT_FILTER = "filter"
const val SERVICE_DATA_EXTRA = "data"
const val SERVICE_TOP_DATA_NAME_EXTRA = "top_name"
const val SERVICE_NO_INTERNET_EXTRA = "no_internet"
const val SERVICE_EMPTY_DATA_EXTRA = "empty_data"

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by viewModels<MainViewModel>()
    private val adapter = MovieAdapter(onListenAction())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recycler.adapter = adapter

        //observeLoadState()
        //observeTopData()
        //listenRetry()

        viewModel.loadDataWithService(requireActivity(), loadResultReceiver)

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_main -> {}
                R.id.bottom_search -> {}
            }


            true
        }
    }

    private fun observeLoadState() {
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
    }

    private fun observeTopData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.top.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun listenRetry() {
        binding.retry.setOnClickListener {
            adapter.retry()
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

    private fun showSnack(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    private val loadResultReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.let {
                showSnack(it.getStringExtra(SERVICE_DATA_EXTRA) ?: throw Exception())
            }
        }
    }

    override fun onDestroy() {
        activity?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultReceiver)
        }
        super.onDestroy()
    }

    companion object {
        const val MOVIE_ID = "movie_id"
    }
}