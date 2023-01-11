package ru.zfix27r.movies.domain

import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.domain.model.FilmResModel

interface FilmRepository {
    fun getFilm(id: Int): Flow<FilmResModel>
}