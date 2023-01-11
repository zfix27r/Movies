package ru.zfix27r.movies.data.remote.mediator

import retrofit2.Response
import ru.zfix27r.movies.data.inspectResponse
import ru.zfix27r.movies.data.local.dao.PremiereDao
import ru.zfix27r.movies.data.local.db.PremiereDb
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.data.remote.model.KinopoiskPremieresResModel
import java.time.LocalDateTime

class PremiereRemoteMediator(
    private val dao: PremiereDao,
    private val api: KinopoiskApi
) : BaseRemoteMediator<PremiereDb, KinopoiskPremieresResModel>() {

    override fun lastItemId(lastItem: PremiereDb?) = lastItem?.premiere?.id

    override suspend fun getRemoteData(page: Int): Response<KinopoiskPremieresResModel> {
        val date = LocalDateTime.now()
        return api.getPremieres(date.year, date.month)
    }

    override suspend fun removeCache() {
        dao.deletePremieres()
    }

    override suspend fun getOldestUpdateAt() = dao.getUpdateTimePremiere()

    override suspend fun saveCache(data: KinopoiskPremieresResModel) {
        dao.saveFilms(data.items.map { it.toFilmEntity() })
        dao.savePremieres(data.items.map { it.toPremiereEntity() })
    }

    override fun pages(data: KinopoiskPremieresResModel) = 1
}