package ru.zfix27r.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.zfix27r.movies.data.local.entity.TopPopularEntity.Companion.TOP_POPULAR_TABLE_NAME

@Entity(
    tableName = TOP_POPULAR_TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = FilmEntity::class,
        parentColumns = [FilmEntity.KINOPOISK_ID],
        childColumns = [TopPopularEntity.FILM_ID_NAME]
    )]
)
data class TopPopularEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = FILM_ID_NAME, index = true)
    val filmId: Int,

    @ColumnInfo(name = UPDATED_AT_NAME)
    val updated_at: Long = System.currentTimeMillis()
) {
    companion object {
        const val TOP_POPULAR_TABLE_NAME = "top_popular"
        const val FILM_ID_NAME = "film_id"
        const val UPDATED_AT_NAME = "updated_at"
    }
}