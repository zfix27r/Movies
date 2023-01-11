package ru.zfix27r.movies.data

import retrofit2.Response
import ru.zfix27r.movies.domain.error.*

fun <T> inspectResponse(response: Response<T>): T {
    response.apply {
        if (isSuccessful) {
            body()?.let { return it }
            throw ResponseEmptyError()
        }

        when (code()) {
            401 -> throw BadTokenError()
            402 -> throw ReachDayLimitError()
            404 -> throw NotFoundError()
            429 -> throw ReachRequestLimitError()
        }
    }
    throw UnknownError()
}