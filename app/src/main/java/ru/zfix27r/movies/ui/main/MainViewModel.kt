package ru.zfix27r.movies.ui.main

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.zfix27r.movies.R
import ru.zfix27r.movies.data.TopRepositoryImpl
import ru.zfix27r.movies.domain.model.MainResModel
import ru.zfix27r.movies.domain.usecase.GetMainMinUseCase
import ru.zfix27r.movies.ui.common.RetryCallback
import ru.zfix27r.movies.ui.getDescriptionOrThrow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TopRepositoryImpl,
    private val getMainMinUseCase: GetMainMinUseCase
) :
    ViewModel(), RetryCallback {

    val isLoading = ObservableBoolean(true)
    val isSuccess = ObservableBoolean(false)
    val error = ObservableInt(R.string.empty)
    private val _data = MutableLiveData<MainResModel>()
    val data: LiveData<MainResModel> = _data

    init {
        loading()
    }

    private fun loading() = viewModelScope.launch(Dispatchers.IO) {
        getMainMinUseCase.execute()
            .onStart {
                isLoading.set(true)
                isSuccess.set(false)
            }
            .catch { e ->
                isLoading.set(false)
                error.set(getDescriptionOrThrow(e))
            }.collectLatest {
                _data.postValue(it)
                isLoading.set(false)
                isSuccess.set(true)
            }
    }

    override fun retry(v: View) {
        error.set(R.string.empty)
        loading()
    }
}