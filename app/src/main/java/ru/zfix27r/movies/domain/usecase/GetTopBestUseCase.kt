package ru.zfix27r.movies.domain.usecase

import ru.zfix27r.movies.domain.TopRepository
import javax.inject.Inject

class GetTopBestUseCase @Inject constructor(private val repository: TopRepository) {
    fun execute(items: Int, pages: Int) = repository.getBest(items, pages)
}