package ru.zfix27r.movies.data

import androidx.paging.*
import kotlinx.coroutines.flow.*
import ru.zfix27r.movies.data.local.MainDao
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.data.remote.PremiereRemoteMediator
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import ru.zfix27r.movies.domain.MainRepository
import ru.zfix27r.movies.domain.model.*
import java.time.LocalDateTime
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dao: MainDao,
    private val api: KinopoiskApi
) :
    MainRepository {
    override fun getMainData() = flow {
        emit(
            MainMinResModel(
                premieres = getPremieres(),
                await = getTopAwait(),
                popular = getTopPopular(),
                best = getTopBest()
            )
        )
    }

    private suspend fun getPremieres(): List<PremiereResModel> {
        val local = dao.getPremiereFirstPage()
        if (local.isNotEmpty()) return local.map { it.toPremiereResModel() }

        val date = LocalDateTime.now()
        val remote = api.getPremieres(date.year, date.month)
        dao.saveFilmList(remote.items.map { it.toFilmEntity() })
        dao.savePremiereList(remote.items.map { it.toPremiereEntity() })
        return remote.items.map { it.toPremiereResModel() }
    }

    private suspend fun getTopBest(): List<TopResModel> {
        val local = dao.getTopBestFirstPage()
        if (local.isNotEmpty()) return local.map { it.toTopResModel() }

        val remote = api.getTop(KinopoiskTopType.TOP_250_BEST_FILMS.name, 1)
        dao.saveFilmList(remote.films.map { it.toFilmEntity() })
        dao.saveTopBestList(remote.films.map { it.toTopBestEntity() })
        return remote.films.map { it.toTopResModel() }
    }

    private suspend fun getTopPopular(): List<TopResModel> {
        val local = dao.getTopPopularFirstPage()
        if (local.isNotEmpty()) return local.map { it.toTopResModel() }

        val remote = api.getTop(KinopoiskTopType.TOP_100_POPULAR_FILMS.name, 1)
        dao.saveFilmList(remote.films.map { it.toFilmEntity() })
        dao.saveTopPopularList(remote.films.map { it.toTopPopularEntity() })
        return remote.films.map { it.toTopResModel() }
    }

    private suspend fun getTopAwait(): List<TopResModel> {
        val local = dao.getTopAwaitFirstPage()
        if (local.isNotEmpty()) return local.map { it.toTopResModel() }

        val remote = api.getTop(KinopoiskTopType.TOP_AWAIT_FILMS.name, 1)
        dao.saveFilmList(remote.films.map { it.toFilmEntity() })
        dao.saveTopAwaitList(remote.films.map { it.toTopAwaitEntity() })
        return remote.films.map { it.toTopResModel() }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingPremieres() = Pager(
        config = PagingConfig(
            pageSize = 9,
            initialLoadSize = 9,
            enablePlaceholders = false
        ),
        remoteMediator = PremiereRemoteMediator(dao, api)
    ) { dao.getAllPremiere() }.flow.map { pd -> pd.map { it.toPremiereResModel() } }

    override fun getTopPagingDataByType(type: KinopoiskTopType): Flow<PagingData<TopResModel>> {
        TODO("Not yet implemented")
    }

    override fun getFilm(id: Int) = flow { emit(dao.getFilm(id).toFilmResModel()) }

/*    @OptIn(ExperimentalPagingApi::class)
    override fun getTopPagingData() = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        remoteMediator = TopRemoteMediator(dao, api)
    ) { dao.getAllTop() }.flow.map { pd -> pd.map { it.toTopResModel() } }*/
}