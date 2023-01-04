package ru.zfix27r.movies.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import retrofit2.HttpException
import ru.zfix27r.movies.data.local.MainDao
import ru.zfix27r.movies.data.local.db.TopBestDb
import ru.zfix27r.movies.data.local.db.TopDb
import ru.zfix27r.movies.data.local.db.TopPopularDb
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class TopPopularRemoteMediator(
    private val dao: MainDao,
    private val api: KinopoiskApi
) : RemoteMediator<Int, TopPopularDb>() {
    override suspend fun initialize(): InitializeAction {
        dao.getLastTimeUpdateTop()?.let {
            if (it + DAY_IN_EPOCH > System.currentTimeMillis())
                return InitializeAction.SKIP_INITIAL_REFRESH
        }
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopPopularDb>
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

            val response = api.getTop(KinopoiskTopType.TOP_100_POPULAR_FILMS.name, page)

            dao.saveFilmList(response.films.map { it.toFilmEntity() })
            dao.saveTopPopularList(response.films.map { it.toTopPopularEntity() })

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