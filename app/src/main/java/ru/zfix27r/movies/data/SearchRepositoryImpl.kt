package ru.zfix27r.movies.data

import androidx.paging.*
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.domain.SearchRepository
import ru.zfix27r.movies.domain.model.*
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi
) :
    SearchRepository {
    override fun getResult(keyword: String): PagingData<BaseResModel> {
        TODO("Not yet implemented")
    }
}