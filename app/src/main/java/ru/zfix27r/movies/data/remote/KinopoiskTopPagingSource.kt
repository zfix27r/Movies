package ru.zfix27r.movies.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.zfix27r.movies.R
import ru.zfix27r.movies.data.local.TopDao
import ru.zfix27r.movies.data.local.db.TopDb
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopEntity
import ru.zfix27r.movies.domain.model.TopResModel
import java.time.LocalDateTime
import kotlin.math.max

private const val STARTING_KEY = 0

class KinopoiskTopPagingSource(
    private val dao: TopDao,
    private val api: KinopoiskApi
) :
    PagingSource<Int, TopResModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopResModel> {
        val key = params.key ?: STARTING_KEY
        println("LOADING")
        var data = getLocalData(key, params.loadSize)
        if (data.isEmpty()) {
            loadRemoteData()?.let {
                return LoadResult.Error(Throwable(it))
            }
            data = getLocalData(key, params.loadSize)
        }

        // FIXME Загрузка топ 250 разом, без постраничной
        return LoadResult.Page(
            data = data.map { it.toTopResModel() },
            prevKey = if (key == 0) null else key - 1,
            nextKey = if (data.size == params.loadSize) key + (params.loadSize / 20) else null
        )
    }

    private suspend fun getLocalData(page: Int, limit: Int) =
        withContext(Dispatchers.IO) {
            return@withContext dao.getTop(page * limit, limit)
        }

    private suspend fun loadRemoteData(): String? = withContext(Dispatchers.IO) {
        try {
            var page = 1
            var id = 1

            while (true) {
                val response = api.getTop(page)
                dao.saveFilmList(response.films.map { it.toFilmEntity() })
                dao.saveTopList(response.films.map { it.toTopEntity(id++) })

                if (response.pagesCount == page) break
                page++
            }
            return@withContext null
        } catch (e: Exception) {
            return@withContext R.string.response_no_internet.toString()
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TopResModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val model = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = model.id - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}