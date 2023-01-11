package ru.zfix27r.movies.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.zfix27r.movies.R
import ru.zfix27r.movies.ui.SingleActivity

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
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