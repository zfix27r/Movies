package ru.zfix27r.movies.domain

import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.domain.model.FilmResModel
import ru.zfix27r.movies.domain.model.TopResModel

interface TopRepository {
    suspend fun getTop(): Flow<List<TopResModel>>
    suspend fun getFilm(id: Long): Flow<FilmResModel>
}