package ru.zfix27r.movies.data.remote.mediator

import ru.zfix27r.movies.data.local.dao.TopDao
import ru.zfix27r.movies.data.local.db.TopAwaitDb
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import ru.zfix27r.movies.data.remote.model.KinopoiskTopResModel

class TopAwaitRemoteMediator(
    private val dao: TopDao,
    private val api: KinopoiskApi,
    private val maxPages: Int? = null
) : BaseRemoteMediator<TopAwaitDb, KinopoiskTopResModel>() {

    override fun lastItemId(lastItem: TopAwaitDb?) = lastItem?.top?.id

    override suspend fun getRemoteData(page: Int) =
        api.getTop(KinopoiskTopType.TOP_AWAIT_FILMS.name, page)

    override suspend fun removeCache() {
        dao.deleteAwaits()
    }

    override suspend fun getOldestUpdateAt() = dao.getUpdateTimeAwait()

    override suspend fun saveCache(data: KinopoiskTopResModel) {
        dao.saveFilms(data.films.map { it.toFilmEntity() })
        dao.saveAwaits(data.films.map { it.toTopAwaitEntity() })
    }

    override fun pages(data: KinopoiskTopResModel) = maxPages ?: data.pagesCount
}