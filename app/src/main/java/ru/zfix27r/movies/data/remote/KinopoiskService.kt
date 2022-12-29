package ru.zfix27r.movies.data.remote

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.zfix27r.movies.ui.main.*

class KinopoiskService(
    name: String = "Kinopoisk"
) : IntentService(name) {
    private val api = KinopoiskApi.create()
    private val bi = Intent(SERVICE_INTENT_FILTER)

    override fun onHandleIntent(p0: Intent?) {
        try {
            val response = api.getTop(1)
            if (response.films.isEmpty()) onEmpty()
            else onSuccess()
        } catch (e: Exception) {
            onError()
        }
    }

    private fun onEmpty() {
        putLoadResult(SERVICE_EMPTY_DATA_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(bi)
    }

    private fun onSuccess() {
        putLoadResult(SERVICE_TOP_DATA_NAME_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(bi)
    }

    private fun onError() {
        putLoadResult(SERVICE_NO_INTERNET_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(bi)
    }

    private fun putLoadResult(name: String) {
        bi.putExtra(SERVICE_DATA_EXTRA, name)
    }
}