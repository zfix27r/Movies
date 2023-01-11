package ru.zfix27r.movies.ui.search

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.zfix27r.movies.R
import ru.zfix27r.movies.domain.model.BaseResModel
import ru.zfix27r.movies.domain.usecase.GetSearchResultUseCase
import ru.zfix27r.movies.ui.getDescriptionOrThrow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase
) : ViewModel() {
    val isLoading = ObservableBoolean(true)
    val isSuccess = ObservableBoolean(false)
    val error = ObservableInt(R.string.empty)
    private val _data = MutableLiveData<List<BaseResModel>>()
    val data: LiveData<List<BaseResModel>> = _data

/*    fun request(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        getSearchResultUseCase.execute(keyword)
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
    }*/

}