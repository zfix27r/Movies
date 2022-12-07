package ru.zfix27r.movies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.zfix27r.movies.KINOPOISKAPIUNOFFICIAL_TECH_KEY
import ru.zfix27r.movies.data.Kinopoisk
import ru.zfix27r.movies.data.FilmTopResponse
import java.io.InputStreamReader
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private var _movies: MutableLiveData<List<FilmTopResponse.Film>> = MutableLiveData()
    val movies: LiveData<List<FilmTopResponse.Film>> = _movies

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val url = URL(Kinopoisk.BASE_URL + Kinopoisk.TOP)
            val connection = url.openConnection() as HttpsURLConnection

            connection.requestMethod = "GET"
            connection.setRequestProperty("X-API-KEY", KINOPOISKAPIUNOFFICIAL_TECH_KEY)
            connection.setRequestProperty("User-Agent", Kinopoisk.HEADER_USER_AGENT)
            connection.setRequestProperty("Content-Type", Kinopoisk.HEADER_CONTENT_TYPE)

            val inputStream = connection.inputStream
            val inputStreamReader = InputStreamReader(inputStream)

            val top = Kinopoisk(inputStreamReader).getTopFilms()
            top.films?.let {
                _movies.postValue(it)
            }
            connection.disconnect()
        }
    }
}