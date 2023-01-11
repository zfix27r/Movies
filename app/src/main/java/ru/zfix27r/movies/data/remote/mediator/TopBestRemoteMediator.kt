package ru.zfix27r.movies.data.remote.mediator

import ru.zfix27r.movies.data.local.dao.FilmDao
import ru.zfix27r.movies.data.local.dao.TopBestDao
import ru.zfix27r.movies.data.local.db.TopBestDb
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import ru.zfix27r.movies.data.remote.model.KinopoiskTopResModel

class TopBestRemoteMediator(
    private val topBestDao: TopBestDao,
    private val filmDao: FilmDao,
    private val api: KinopoiskApi
) : BaseRemoteMediator<TopBestDb, KinopoiskTopResModel>() {
    override suspend fun getRemoteData(page: Int) =
        api.getTop(KinopoiskTopType.TOP_250_BEST_FILMS.name, page)

    override suspend fun removeCache() {
        topBestDao.delete()
    }

    override suspend fun getOldestUpdateAt() = topBestDao.getOldestUpdateAt()

    override suspend fun saveCache(data: KinopoiskTopResModel) {
        filmDao.save(data.films.map { it.toFilmEntity() })
        topBestDao.save(data.films.map { it.toTopBestEntity() })
    }

    override fun lastItemId(lastItem: TopBestDb?) = lastItem?.top?.id

    override fun pages(data: KinopoiskTopResModel) = data.pagesCount
}