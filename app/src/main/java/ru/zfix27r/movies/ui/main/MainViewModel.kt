package ru.zfix27r.movies.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.zfix27r.movies.domain.model.TopResModel
import ru.zfix27r.movies.domain.usecase.GetTopUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopUseCase: GetTopUseCase
) :
    ViewModel() {

    val isLoading: ObservableBoolean = ObservableBoolean(true)

    private var _movies: MutableLiveData<List<TopResModel>> = MutableLiveData()
    val movies: LiveData<List<TopResModel>> = _movies

    private var _response: MutableLiveData<Throwable> = MutableLiveData()
    val response: LiveData<Throwable> = _response

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTopUseCase.execute()
                .onStart { isLoading.set(true) }
                .onEach {
                    isLoading.set(false)
                    _movies.postValue(it)
                }
                .catch { _response.postValue(it) }
                .collect()
        }
    }
}