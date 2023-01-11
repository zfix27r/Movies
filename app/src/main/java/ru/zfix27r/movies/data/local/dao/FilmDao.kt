package ru.zfix27r.movies.data.local.dao

import androidx.room.*
import ru.zfix27r.movies.data.local.db.*
import ru.zfix27r.movies.data.local.entity.*

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(list: List<FilmEntity>)

    @Query("SELECT * FROM film WHERE kinopoisk_id = :id LIMIT 1")
    fun getFilm(id: Int): FilmEntity

    @Query("SELECT updated_at FROM top_best ORDER BY updated_at DESC LIMIT 1")
    suspend fun getOldestUpdateTop(): Long?
}