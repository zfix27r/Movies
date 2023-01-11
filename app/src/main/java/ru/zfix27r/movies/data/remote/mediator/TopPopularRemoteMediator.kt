package ru.zfix27r.movies.data.remote.mediator

import ru.zfix27r.movies.data.local.dao.TopDao
import ru.zfix27r.movies.data.local.db.TopPopularDb
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import ru.zfix27r.movies.data.remote.model.KinopoiskTopResModel

class TopPopularRemoteMediator(
    private val dao: TopDao,
    private val api: KinopoiskApi,
    private val maxPages: Int? = null
) : BaseRemoteMediator<TopPopularDb, KinopoiskTopResModel>() {

    override fun lastItemId(lastItem: TopPopularDb?) = lastItem?.top?.id

    override suspend fun getRemoteData(page: Int) =
        api.getTop(KinopoiskTopType.TOP_100_POPULAR_FILMS.name, page)

    override suspend fun removeCache() {
        dao.deletePopulars()
    }

    override suspend fun getOldestUpdateAt() = dao.getUpdateTimePopular()

    override suspend fun saveCache(data: KinopoiskTopResModel) {
        dao.saveFilms(data.films.map { it.toFilmEntity() })
        dao.savePopulars(data.films.map { it.toTopPopularEntity() })
    }

    override fun pages(data: KinopoiskTopResModel) = maxPages ?: data.pagesCount
}