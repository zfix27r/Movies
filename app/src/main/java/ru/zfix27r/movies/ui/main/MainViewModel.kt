package ru.zfix27r.movies.ui.main

import android.view.View
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
import ru.zfix27r.movies.domain.model.PremiereResModel
import ru.zfix27r.movies.domain.model.TopResModel
import ru.zfix27r.movies.domain.usecase.GetMainMinUseCase
import ru.zfix27r.movies.ui.common.RetryCallback
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMainMinUseCase: GetMainMinUseCase
) :
    ViewModel(), RetryCallback {

    val isLoading = ObservableBoolean(true)
    val error = ObservableInt(R.string.empty)

    private var _premieres: MutableLiveData<List<PremiereResModel>> = MutableLiveData()
    val premieres: LiveData<List<PremiereResModel>> = _premieres

    private var _await: MutableLiveData<List<TopResModel>> = MutableLiveData()
    val await: LiveData<List<TopResModel>> = _await

    private var _popular: MutableLiveData<List<TopResModel>> = MutableLiveData()
    val popular: LiveData<List<TopResModel>> = _popular

    private var _best: MutableLiveData<List<TopResModel>> = MutableLiveData()
    val best: LiveData<List<TopResModel>> = _best

    private fun loading() = viewModelScope.launch(Dispatchers.IO) {
        getMainMinUseCase.execute()
            .onStart { isLoading.set(true) }
            .catch { e ->
                isLoading.set(false)
                when (e) {
                    else -> error.set(R.string.response_no_internet)
                }
            }
            .collectLatest {
                _premieres.postValue(it.premieres)
                _await.postValue(it.await)
                _popular.postValue(it.popular)
                _best.postValue(it.best)
                println(it)

                isLoading.set(false)
            }
    }

    init {
        loading()
    }

    override fun retry(v: View) {
        error.set(R.string.empty)
        loading()
    }


    /*    val premieres: Flow<List<PremiereResModel>> = getPremieresUseCase.execute()
            .onCompletion {
                isLoadPremieres = true
                updateLoading()
            }.catch {

            }*/
    /*   val best: Flow<List<TopResModel>> = getTopBestUseCase.execute().onCompletion {
           isLoadBest = true
           updateLoading()
       }
       val popular: Flow<List<TopResModel>> = getTopPopularUseCase.execute().onCompletion {
           isLoadPopular = true
           updateLoading()
       }
       val await: Flow<List<TopResModel>> = getTopAwaitUseCase.execute().onCompletion {
           isLoadAwait = true
           updateLoading()
       }*/
/*
    private fun updateLoading() {
        if (isLoadPremieres && isLoadBest && isLoadPopular && isLoadAwait)
            isLoading.set(false)
    }*/
/*    val top: Flow<PagingData<TopResModel>> =
        getTopPagingSourceUseCase.execute().cachedIn(viewModelScope)*/
}