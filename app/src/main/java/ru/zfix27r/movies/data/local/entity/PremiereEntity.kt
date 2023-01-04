package ru.zfix27r.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.zfix27r.movies.data.local.entity.PremiereEntity.Companion.PREMIER_TABLE_NAME

@Entity(
    tableName = PREMIER_TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = FilmEntity::class,
        parentColumns = [FilmEntity.KINOPOISK_ID],
        childColumns = [PremiereEntity.FILM_ID_NAME]
    )]
)
data class PremiereEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = FILM_ID_NAME, index = true)
    val filmId: Int,

    @ColumnInfo(name = PREMIER_RU_NAME)
    val premiereRu: String,

    @ColumnInfo(name = UPDATED_AT_NAME)
    val updated_at: Long = System.currentTimeMillis()
) {
    companion object {
        const val PREMIER_TABLE_NAME = "premier"
        const val FILM_ID_NAME = "film_id"
        const val PREMIER_RU_NAME = "premier_ru"
        const val UPDATED_AT_NAME = "updated_at"
    }
}