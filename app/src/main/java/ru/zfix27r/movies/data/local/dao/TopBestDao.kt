package ru.zfix27r.movies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.zfix27r.movies.data.local.db.TopBestDb
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopBestEntity

@Dao
interface TopBestDao {
    @Transaction
    @Query("SELECT * FROM top_best")
    fun getAll(): PagingSource<Int, TopBestDb>

    @Transaction
    @Query("SELECT * FROM top_best LIMIT :limit")
    fun getByLimit(limit: Int): PagingSource<Int, TopBestDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(list: List<TopBestEntity>)

    @Query("DELETE FROM top_best")
    suspend fun delete()

    @Query("SELECT updated_at FROM top_best ORDER BY updated_at DESC LIMIT 1")
    suspend fun getOldestUpdateAt(): Long?
}