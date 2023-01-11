package ru.zfix27r.movies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.zfix27r.movies.data.local.dao.MainDao
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import ru.zfix27r.movies.data.remote.mediator.TopBestRemoteMediator
import ru.zfix27r.movies.domain.MainRepository
import ru.zfix27r.movies.domain.model.MainResModel
import ru.zfix27r.movies.domain.model.PremiereResModel
import ru.zfix27r.movies.domain.model.BaseResModel
import java.time.LocalDateTime
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dao: MainDao,
    private val api: KinopoiskApi
) :
    MainRepository {
    override fun getData() = flow {
        emit(
            MainResModel(
                getPremieres(),
                getTopAwait(),
                getTopPopular(),
                getTopBest()
            )
        )
    }

    private suspend fun getPremieres(): List<PremiereResModel> {
        dao.getPremiereFirstPage().apply {
            if (isNotEmpty()) return map { it.toPremiereResModel() }
        }

        val date = LocalDateTime.now()
        val response = api.getPremieres(date.year, date.month)
        inspectResponse(response).apply {
            dao.saveFilmList(items.map { it.toFilmEntity() })
            dao.savePremiereList(items.map { it.toPremiereEntity() })
            return items.map { it.toPremiereResModel() }
        }
    }

    private suspend fun getTopAwait(): List<BaseResModel> {
        dao.getTopAwaitFirstPage().apply {
            if (isNotEmpty()) return map { it.toTopResModel() }
        }

        val response = api.getTop(KinopoiskTopType.TOP_AWAIT_FILMS.name, 1)
        inspectResponse(response).apply {
            dao.saveFilmList(films.map { it.toFilmEntity() })
            dao.saveTopAwaitList(films.map { it.toTopAwaitEntity() })
            return films.map { it.toTopResModel() }
        }
    }

    private suspend fun getTopPopular(): List<BaseResModel> {
        dao.getTopPopularFirstPage().apply {
            if (isNotEmpty()) return map { it.toTopResModel() }
        }

        val response = api.getTop(KinopoiskTopType.TOP_100_POPULAR_FILMS.name, 1)
        inspectResponse(response).apply {
            dao.saveFilmList(films.map { it.toFilmEntity() })
            dao.saveTopPopularList(films.map { it.toTopPopularEntity() })
            return films.map { it.toTopResModel() }
        }
    }

    private suspend fun getTopBest(): List<BaseResModel> {
        dao.getTopBestFirstPage().apply {
            if (isNotEmpty()) return map { it.toTopResModel() }
        }

        val response = api.getTop(KinopoiskTopType.TOP_250_BEST_FILMS.name, 1)
        inspectResponse(response).apply {
            dao.saveFilmList(films.map { it.toFilmEntity() })
            dao.saveTopBestList(films.map { it.toTopBestEntity() })
            return films.map { it.toTopResModel() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getBest(items: Int, pages: Int) = Pager(
        config = PagingConfig(
            pageSize = items,
            enablePlaceholders = false,
            initialLoadSize = items
        ),
        remoteMediator = TopBestRemoteMediator(topBestDao, filmDao, api)
    ) { topBestDao.getByLimit(items * pages) }.flow.map { pd -> pd.map { it.toTopResModel() } }

    override fun getData(items: Int, pages: Int) = flow {
        emit(
            MainResModel(
                premieres =
            )
        )
    }
}