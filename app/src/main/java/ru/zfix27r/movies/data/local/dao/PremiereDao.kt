package ru.zfix27r.movies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import ru.zfix27r.movies.data.local.db.*
import ru.zfix27r.movies.data.local.entity.*

@Dao
interface PremiereDao {
    @Transaction
    @Query("SELECT * FROM premiere")
    fun getPremieresPagingSource(): PagingSource<Int, PremiereDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePremieres(list: List<PremiereEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilms(list: List<FilmEntity>)

    @Query("DELETE FROM top_await")
    suspend fun deletePremieres()

    @Query("SELECT updated_at FROM premiere ORDER BY updated_at DESC LIMIT 1")
    suspend fun getUpdateTimePremiere(): Long?
}