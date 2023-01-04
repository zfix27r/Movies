package ru.zfix27r.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.zfix27r.movies.data.local.entity.TopAwaitEntity.Companion.TOP_AWAIT_TABLE_NAME
import ru.zfix27r.movies.data.local.entity.TopBestEntity.Companion.TOP_BEST_TABLE_NAME

@Entity(
    tableName = TOP_AWAIT_TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = FilmEntity::class,
        parentColumns = [FilmEntity.KINOPOISK_ID],
        childColumns = [TopAwaitEntity.FILM_ID_NAME]
    )]
)
data class TopAwaitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = FILM_ID_NAME, index = true)
    val filmId: Int,

    @ColumnInfo(name = UPDATED_AT_NAME)
    val updated_at: Long = System.currentTimeMillis()
) {
    companion object {
        const val TOP_AWAIT_TABLE_NAME = "top_await"
        const val FILM_ID_NAME = "film_id"
        const val UPDATED_AT_NAME = "updated_at"
    }
}