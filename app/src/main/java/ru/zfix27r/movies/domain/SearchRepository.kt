package ru.zfix27r.movies.domain

import androidx.paging.PagingData
import ru.zfix27r.movies.domain.model.BaseResModel

interface SearchRepository {
    fun getResult(keyword: String): PagingData<BaseResModel>
}