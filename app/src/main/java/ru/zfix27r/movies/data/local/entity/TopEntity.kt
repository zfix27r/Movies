package ru.zfix27r.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.zfix27r.movies.data.local.entity.TopEntity.Companion.TOP_TABLE_NAME

@Entity(
    tableName = TOP_TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = FilmEntity::class,
        parentColumns = [FilmEntity.KINOPOISK_ID],
        childColumns = [TopEntity.FILM_ID]
    )]
)
data class TopEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = FILM_ID)
    val filmId: Int,

    @ColumnInfo(name = UPDATED_AT)
    val updated_at: String
) {
    companion object {
        const val TOP_TABLE_NAME = "top"

        const val FILM_ID = "film_id"

        const val UPDATED_AT = "updated_at"
    }
}