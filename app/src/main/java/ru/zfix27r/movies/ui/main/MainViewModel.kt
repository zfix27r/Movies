package ru.zfix27r.movies.ui.main

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.zfix27r.movies.data.remote.KinopoiskService
import ru.zfix27r.movies.domain.model.TopResModel
import ru.zfix27r.movies.domain.usecase.GetTopPagingDataUseCase
import ru.zfix27r.movies.ui.MainActivity
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getTopPagingSourceUseCase: GetTopPagingDataUseCase
) :
    ViewModel() {
    val top: Flow<PagingData<TopResModel>> =
        getTopPagingSourceUseCase.execute().cachedIn(viewModelScope)

    fun loadDataWithService(activity: Activity, broadcastReceiver: BroadcastReceiver) =
        viewModelScope.launch(Dispatchers.IO) {
            LocalBroadcastManager.getInstance(activity).registerReceiver(
                broadcastReceiver, IntentFilter(SERVICE_INTENT_FILTER)
            )
            activity.startService(Intent(activity, KinopoiskService::class.java))
        }
}