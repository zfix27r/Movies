package ru.zfix27r.movies.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.domain.model.BaseResModel

interface TopRepository {
    fun getAwait(): Flow<PagingData<BaseResModel>>
    fun getPopular(): Flow<PagingData<BaseResModel>>
    fun getBest(items: Int, pages: Int): Flow<PagingData<BaseResModel>>
}