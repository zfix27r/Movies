package ru.zfix27r.movies.domain.usecase

import ru.zfix27r.movies.domain.MainRepository
import javax.inject.Inject

class GetMainDataUseCase @Inject constructor(private val repository: MainRepository) {
    fun execute() = repository.getData()
}