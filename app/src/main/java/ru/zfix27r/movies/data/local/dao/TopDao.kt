package ru.zfix27r.movies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import ru.zfix27r.movies.data.local.db.*
import ru.zfix27r.movies.data.local.entity.*

@Dao
interface TopDao {
    @Transaction
    @Query("SELECT * FROM top_await")
    fun getAwaitsPagingSource(): PagingSource<Int, TopAwaitDb>

    @Transaction
    @Query("SELECT * FROM top_popular")
    fun getPopularsPagingSource(): PagingSource<Int, TopPopularDb>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAwaits(list: List<TopAwaitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePopulars(list: List<TopPopularEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilms(list: List<FilmEntity>)

    @Query("DELETE FROM top_await")
    suspend fun deleteAwaits()

    @Query("DELETE FROM top_popular")
    suspend fun deletePopulars()


    @Query("SELECT updated_at FROM top_await ORDER BY updated_at DESC LIMIT 1")
    suspend fun getUpdateTimeAwait(): Long?

    @Query("SELECT updated_at FROM top_popular ORDER BY updated_at DESC LIMIT 1")
    suspend fun getUpdateTimePopular(): Long?

}