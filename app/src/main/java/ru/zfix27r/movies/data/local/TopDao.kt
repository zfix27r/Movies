package ru.zfix27r.movies.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopEntity
import ru.zfix27r.movies.data.local.entity.top.TopDb

@Dao
interface TopDao {
    @Transaction
    @Query("SELECT * FROM top")
    fun getTop(): Flow<List<TopDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilmList(list: List<FilmEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTopList(list: List<TopEntity>)

    @Query("SELECT * FROM film WHERE kinopoisk_id = :id LIMIT 1")
    fun getFilm(id: Long): FilmEntity
}