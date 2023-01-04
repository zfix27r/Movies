package ru.zfix27r.movies.domain.usecase

import ru.zfix27r.movies.domain.MainRepository
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(private val repository: MainRepository) {
    fun execute(id: Int) = repository.getFilm(id)
}