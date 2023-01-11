package ru.zfix27r.movies.ui

import ru.zfix27r.movies.R
import ru.zfix27r.movies.domain.error.*
import java.net.UnknownHostException

fun getDescriptionOrThrow(e: Throwable): Int {
    when (e) {
        is UnknownHostException -> R.string.error_no_internet
        is BadTokenError -> R.string.error_bad_token
        is NotFoundError -> R.string.error_not_found
        is ReachDayLimitError -> R.string.error_reach_day_limit
        is ReachRequestLimitError -> R.string.error_reach_request_limit
        is ResponseEmptyError -> R.string.error_empty_response
    }

    throw Exception(e.message)
}