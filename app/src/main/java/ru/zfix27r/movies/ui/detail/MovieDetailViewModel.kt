package ru.zfix27r.movies.ui.detail

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.zfix27r.movies.domain.model.FilmResModel
import ru.zfix27r.movies.domain.usecase.GetFilmUseCase
import ru.zfix27r.movies.ui.main.MainFragment.Companion.MOVIE_ID
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getFilmUseCase: GetFilmUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _movie: MutableLiveData<FilmResModel> = MutableLiveData()
    val movie: LiveData<FilmResModel> = _movie

    private var _response: MutableLiveData<Throwable> = MutableLiveData()
    val response: LiveData<Throwable> = _response

    private val movieId = savedStateHandle.get<Int>(MOVIE_ID) ?: 0

    init {
        if (movieId == 0) throw Exception("MovieDetailViewModel получен пустой id")
        loadNote()
    }

    private fun loadNote() = viewModelScope.launch(Dispatchers.IO) {
        getFilmUseCase.execute(movieId)
            .onEach { _movie.postValue(it) }
            .catch { _response.postValue(it) }
            .collect()
    }
}