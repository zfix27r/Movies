package ru.zfix27r.movies.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import retrofit2.HttpException
import ru.zfix27r.movies.data.local.MainDao
import ru.zfix27r.movies.data.local.db.PremiereDb
import ru.zfix27r.movies.data.local.db.TopAwaitDb
import ru.zfix27r.movies.data.local.db.TopBestDb
import ru.zfix27r.movies.data.local.db.TopDb
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import java.io.IOException
import java.time.LocalDateTime


@OptIn(ExperimentalPagingApi::class)
class PremiereRemoteMediator(
    private val dao: MainDao,
    private val api: KinopoiskApi
) : RemoteMediator<Int, PremiereDb>() {
    override suspend fun initialize(): InitializeAction {
        dao.getLastTimeUpdateTop()?.let {
            if (it + DAY_IN_EPOCH > System.currentTimeMillis())
                return InitializeAction.SKIP_INITIAL_REFRESH
        }
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PremiereDb>
    ): MediatorResult {
        return try {
            val id = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()?.premiere?.id
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val date = LocalDateTime.now()
            val response = api.getPremieres(date.year, date.month)

            dao.saveFilmList(response.items.map { it.toFilmEntity() })
            dao.savePremiereList(response.items.map { it.toPremiereEntity() })

            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}