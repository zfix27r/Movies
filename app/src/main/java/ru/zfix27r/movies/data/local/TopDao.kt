package ru.zfix27r.movies.data.local

import androidx.paging.PagingSource
import androidx.room.*
import ru.zfix27r.movies.data.local.db.TopDb
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopEntity

@Dao
interface TopDao {
    @Query("SELECT * FROM top LIMIT :limit OFFSET :offset")
    fun getTop(offset: Int, limit: Int): List<TopDb>

    @Query("SELECT * FROM top")
    fun getAllTop(): PagingSource<Int, TopDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilmList(list: List<FilmEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTopList(list: List<TopEntity>)

    @Query("SELECT * FROM film WHERE kinopoisk_id = :id LIMIT 1")
    fun getFilm(id: Int): FilmEntity
}