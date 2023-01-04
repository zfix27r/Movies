package ru.zfix27r.movies.domain.usecase

import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import ru.zfix27r.movies.domain.MainRepository
import javax.inject.Inject

class GetTopPagingDataUseCase @Inject constructor(private val repository: MainRepository) {
    fun execute() = repository.getTopPagingDataByType(KinopoiskTopType.TOP_250_BEST_FILMS)
}