package ru.zfix27r.movies.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import ru.zfix27r.movies.domain.model.FilmResModel
import ru.zfix27r.movies.domain.model.MainMinResModel
import ru.zfix27r.movies.domain.model.PremiereResModel
import ru.zfix27r.movies.domain.model.TopResModel

interface MainRepository {
    fun getMainData(): Flow<MainMinResModel>

    fun getPagingPremieres(): Flow<PagingData<PremiereResModel>>
    //fun getPagingTopBest(): Flow<PagingData<TopResModel>>

    fun getTopPagingDataByType(type: KinopoiskTopType): Flow<PagingData<TopResModel>>

    fun getFilm(id: Int): Flow<FilmResModel>
}