package ru.zfix27r.movies.domain.usecase

import ru.zfix27r.movies.domain.FilmTopRepository
import javax.inject.Inject

class GetTopFilmsUseCase @Inject constructor(private val repository: FilmTopRepository) {
    suspend fun execute() = repository.getTopFilms()
}