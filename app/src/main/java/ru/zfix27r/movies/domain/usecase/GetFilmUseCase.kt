package ru.zfix27r.movies.domain.usecase

import ru.zfix27r.movies.domain.TopRepository
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(private val repository: TopRepository) {
    suspend fun execute(id: Long) = repository.getFilm(id)
}