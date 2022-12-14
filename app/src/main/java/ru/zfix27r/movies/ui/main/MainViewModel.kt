package ru.zfix27r.movies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.zfix27r.movies.domain.model.TopResModel
import ru.zfix27r.movies.domain.usecase.GetTopPagingDataUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getTopPagingSourceUseCase: GetTopPagingDataUseCase
) :
    ViewModel() {
    val top: Flow<PagingData<TopResModel>> =
        getTopPagingSourceUseCase.execute().cachedIn(viewModelScope)
}