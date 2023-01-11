package ru.zfix27r.movies.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.domain.model.PremiereResModel

interface PremiereRepository {
    fun getPremieres(): Flow<PagingData<PremiereResModel>>
}