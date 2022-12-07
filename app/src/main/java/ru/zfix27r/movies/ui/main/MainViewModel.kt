package ru.zfix27r.movies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.zfix27r.movies.data.film.FilmTopFilm
import ru.zfix27r.movies.domain.usecase.GetTopFilmsUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopFilmsUseCase: GetTopFilmsUseCase
) :
    ViewModel() {

    private var _movies: MutableLiveData<List<FilmTopFilm>> = MutableLiveData()
    val movies: LiveData<List<FilmTopFilm>> = _movies

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTopFilmsUseCase.execute()
                .onEach { _movies.postValue(it) }
                .collect()
        }
    }
}