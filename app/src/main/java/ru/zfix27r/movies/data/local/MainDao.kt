package ru.zfix27r.movies.data.local

import androidx.paging.PagingSource
import androidx.room.*
import ru.zfix27r.movies.data.local.db.*
import ru.zfix27r.movies.data.local.entity.*

@Dao
interface MainDao {
    @Transaction
    @Query("SELECT * FROM premier LIMIT 10")
    suspend fun getPremiereFirstPage() : List<PremiereDb>

    @Transaction
    @Query("SELECT * FROM top_best LIMIT 10")
    suspend fun getTopBestFirstPage() : List<TopBestDb>

    @Transaction
    @Query("SELECT * FROM top_popular LIMIT 10")
    suspend fun getTopPopularFirstPage() : List<TopPopularDb>

    @Transaction
    @Query("SELECT * FROM top_await LIMIT 10")
    suspend fun getTopAwaitFirstPage() : List<TopAwaitDb>

    @Transaction
    @Query("SELECT * FROM top_best")
    fun getAllTop(): PagingSource<Int, TopDb>

    @Transaction
    @Query("SELECT * FROM premier")
    fun getAllPremiere(): PagingSource<Int, PremiereDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePremiereList(list: List<PremiereEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTopBestList(list: List<TopBestEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTopPopularList(list: List<TopPopularEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTopAwaitList(list: List<TopAwaitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilmList(list: List<FilmEntity>)


    @Query("SELECT * FROM film WHERE kinopoisk_id = :id LIMIT 1")
    fun getFilm(id: Int): FilmEntity

    @Query("SELECT updated_at FROM top_best ORDER BY updated_at DESC LIMIT 1")
    suspend fun getLastTimeUpdateTop(): Long?
}