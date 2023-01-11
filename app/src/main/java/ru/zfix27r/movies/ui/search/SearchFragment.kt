package ru.zfix27r.movies.ui.search

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentSearchBinding
import ru.zfix27r.movies.ui.SingleActivity

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.addOnEditTextAttachedListener {
            println(it)
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