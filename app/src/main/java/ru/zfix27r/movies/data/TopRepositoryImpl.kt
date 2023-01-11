package ru.zfix27r.movies.data

import androidx.paging.*
import kotlinx.coroutines.flow.*
import ru.zfix27r.movies.data.local.dao.FilmDao
import ru.zfix27r.movies.data.local.dao.TopBestDao
import ru.zfix27r.movies.data.local.dao.TopDao
import ru.zfix27r.movies.data.remote.*
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.data.remote.mediator.TopAwaitRemoteMediator
import ru.zfix27r.movies.data.remote.mediator.TopBestRemoteMediator
import ru.zfix27r.movies.data.remote.mediator.TopPopularRemoteMediator
import ru.zfix27r.movies.domain.TopRepository
import ru.zfix27r.movies.domain.model.*
import javax.inject.Inject

class TopRepositoryImpl @Inject constructor(
    private val dao: TopDao,
    private val topBestDao: TopBestDao,
    private val filmDao: FilmDao,
    private val api: KinopoiskApi
) :
    TopRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun getAwait() = Pager(
        config = PagingConfig(
            pageSize = 12,
            enablePlaceholders = false
        ),
        remoteMediator = TopAwaitRemoteMediator(dao, api)
    ) { dao.getAwaitsPagingSource() }.flow.map { pd -> pd.map { it.toTopResModel() } }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPopular() = Pager(
        config = PagingConfig(
            pageSize = 12,
            enablePlaceholders = false
        ),
        remoteMediator = TopPopularRemoteMediator(dao, api)
    ) { dao.getPopularsPagingSource() }.flow.map { pd -> pd.map { it.toTopResModel() } }

    @OptIn(ExperimentalPagingApi::class)
    override fun getBest(items: Int, pages: Int) = Pager(
        config = PagingConfig(
            pageSize = items,
            enablePlaceholders = false,
            initialLoadSize = items
        ),
        remoteMediator = TopBestRemoteMediator(topBestDao, filmDao, api)
    ) { topBestDao.getByLimit(items * pages) }.flow.map { pd -> pd.map { it.toTopResModel() } }
}