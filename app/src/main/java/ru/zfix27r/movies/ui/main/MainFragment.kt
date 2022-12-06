package ru.zfix27r.movies.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.zfix27r.movies.R
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val githubEndpoint = URL("https://api.github.com/")
        val myConnection: HttpsURLConnection = githubEndpoint.openConnection() as HttpsURLConnection
        myConnection.setRequestProperty("X-API-KEY", "zfix27r")
        myConnection.setRequestProperty("User-Agent", "zfix27r")



        return inflater.inflate(R.layout.fragment_main, container, false)
    }

}