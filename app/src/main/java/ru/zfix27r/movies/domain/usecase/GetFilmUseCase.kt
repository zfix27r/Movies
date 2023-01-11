package ru.zfix27r.movies.domain.usecase

import ru.zfix27r.movies.domain.FilmRepository
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(private val repository: FilmRepository) {
    fun execute(id: Int) = repository.getFilm(id)
}