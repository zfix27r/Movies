package ru.zfix27r.movies.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import retrofit2.HttpException
import ru.zfix27r.movies.data.local.MainDao
import ru.zfix27r.movies.data.local.db.TopDb
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import java.io.IOException

const val DAY_IN_EPOCH = 86400000

@OptIn(ExperimentalPagingApi::class)
class TopRemoteMediator(
    private val dao: MainDao,
    private val api: KinopoiskApi
) : RemoteMediator<Int, TopDb>() {
    override suspend fun initialize(): InitializeAction {
        dao.getLastTimeUpdateTop()?.let {
            if (it + DAY_IN_EPOCH > System.currentTimeMillis())
                return InitializeAction.SKIP_INITIAL_REFRESH
        }
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopDb>
    ): MediatorResult {
        return try {
            val id = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()?.top?.id
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val page = if (id == 1) 1 else id / 20 + 1

            val response = api.getTop(KinopoiskTopType.TOP_250_BEST_FILMS.name, page)

            dao.saveFilmList(response.films.map { it.toFilmEntity() })
            dao.saveTopBestList(response.films.map { it.toTopBestEntity() })

            MediatorResult.Success(
                endOfPaginationReached = response.pagesCount == page
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}