package ru.zfix27r.movies.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import retrofit2.HttpException
import retrofit2.Response
import ru.zfix27r.movies.data.inspectResponse
import java.io.IOException

const val DAY_IN_EPOCH = 86400000

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<T : Any, V : Any> : RemoteMediator<Int, T>() {
    abstract suspend fun getOldestUpdateAt(): Long?
    abstract suspend fun getRemoteData(page: Int): Response<V>
    abstract suspend fun saveCache(data: V)
    abstract suspend fun removeCache()
    abstract fun lastItemId(lastItem: T?): Int?
    abstract fun pages(data: V): Int

    override suspend fun initialize(): InitializeAction {
        println("INIT")
        getOldestUpdateAt()?.let {
            if (it + DAY_IN_EPOCH > System.currentTimeMillis())
                return InitializeAction.SKIP_INITIAL_REFRESH
            else
                removeCache()
        }
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>
    ): MediatorResult {
        println("LOAD")
        return try {
            val id = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    println("LOAD items = " + lastItemId(state.lastItemOrNull()))
                    lastItemId(state.lastItemOrNull())
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            MediatorResult.Success(endOfPaginationReached = true)

            val pages: Int
            val page = if (id == 1) 1 else id / 20 + 1

            getRemoteData(page).apply {
                inspectResponse(this).apply {
                    saveCache(this)
                    pages = pages(this)
                }
            }

            println("LOAD FULL")
            MediatorResult.Success(endOfPaginationReached = pages == page)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}