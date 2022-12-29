package ru.zfix27r.movies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.insertSeparators
import androidx.paging.map
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.zfix27r.movies.data.local.TopDao
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.data.remote.KinopoiskTopPagingSource
import ru.zfix27r.movies.domain.TopRepository
import ru.zfix27r.movies.domain.model.TopResModel
import javax.inject.Inject

class TopRepositoryImpl @Inject constructor(
    private val dao: TopDao,
    private val api: KinopoiskApi
) :
    TopRepository {

    override fun getFilm(id: Int) = flow { emit(dao.getFilm(id).toFilmResModel()) }

    override fun getTopPagingData() = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            maxSize = 200
        ),
        pagingSourceFactory = { KinopoiskTopPagingSource(dao, api) }
    ).flow
}