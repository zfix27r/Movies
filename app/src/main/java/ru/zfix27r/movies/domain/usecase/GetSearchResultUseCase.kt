package ru.zfix27r.movies.domain.usecase

import ru.zfix27r.movies.domain.SearchRepository
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(private val repository: SearchRepository) {
    fun execute(keyword: String) = repository.getResult(keyword)
}