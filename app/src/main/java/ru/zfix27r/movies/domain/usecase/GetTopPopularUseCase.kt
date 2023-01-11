package ru.zfix27r.movies.domain.usecase

import ru.zfix27r.movies.domain.TopRepository
import javax.inject.Inject

class GetTopPopularUseCase @Inject constructor(private val repository: TopRepository) {
    fun execute() = repository.getPopular()
}