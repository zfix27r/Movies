package ru.zfix27r.movies.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.domain.model.FilmResModel
import ru.zfix27r.movies.domain.model.TopResModel

interface TopRepository {
    fun getTopPagingData(): Flow<PagingData<TopResModel>>

    fun getFilm(id: Int): Flow<FilmResModel>
}