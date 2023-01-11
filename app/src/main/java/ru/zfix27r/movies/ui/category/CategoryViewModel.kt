package ru.zfix27r.movies.ui.category

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.zfix27r.movies.R
import ru.zfix27r.movies.data.TopRepositoryImpl
import ru.zfix27r.movies.domain.usecase.GetTopAwaitUseCase
import ru.zfix27r.movies.domain.usecase.GetTopBestUseCase
import ru.zfix27r.movies.domain.usecase.GetTopPopularUseCase
import javax.inject.Inject

const val TOP_TYPE = "top_type"

@HiltViewModel
class TopViewModel @Inject constructor(
    private val repository: TopRepositoryImpl,
    getTopAwaitUseCase: GetTopAwaitUseCase,
    getTopPopularUseCase: GetTopPopularUseCase,
    getTopBestUseCase: GetTopBestUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val isLoading = ObservableBoolean(true)
    val error = ObservableInt(R.string.empty)

    private val listType: Categories? by lazy {
        savedStateHandle.get<String>(TOP_TYPE)?.let {
            Categories.valueOf(it)
        }
    }


    val top = when (listType) {
        Categories.TOP_AWAIT -> getTopAwaitUseCase.execute()
        Categories.TOP_POPULAR -> getTopPopularUseCase.execute()
        Categories.TOP_BEST -> getTopBestUseCase.execute(12, 1)
        else -> throw Exception("нет реализации")
    }
}