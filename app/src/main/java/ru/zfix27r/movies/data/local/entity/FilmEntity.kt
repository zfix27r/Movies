package ru.zfix27r.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.zfix27r.movies.data.local.entity.FilmEntity.Companion.FILM_TABLE_NAME
import ru.zfix27r.movies.domain.model.FilmResModel

@Entity(
    tableName = FILM_TABLE_NAME
)
data class FilmEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = KINOPOISK_ID)
    val kinopoiskId: Int,

    @ColumnInfo(name = NAME_RU)
    val nameRu: String,
    @ColumnInfo(name = POSTER_URL)
    val posterUrl: String,
    @ColumnInfo(name = POSTER_URL_PREVIEW)
    val posterUrlPreview: String,

    @ColumnInfo(name = KINOPOISK_RATING)
    val kinopoiskRating: Float,
    @ColumnInfo(name = KINOPOISK_RATING_VOTE_COUNT)
    val kinopoiskRatingVoteCount: String,

    @ColumnInfo(name = UPDATED_AT)
    val updated_at: String
) {
    fun toFilmResModel() = FilmResModel(
        id = kinopoiskId,
        nameRu = nameRu,
        posterUrl = posterUrl,
        rating = kinopoiskRating,
        ratingVoteCount = kinopoiskRatingVoteCount
    )

    companion object {
        const val FILM_TABLE_NAME = "film"

        const val NAME_RU = "name_ru"
        const val POSTER_URL = "poster_url"
        const val POSTER_URL_PREVIEW = "poster_url_preview"

        const val KINOPOISK_ID = "kinopoisk_id"
        const val KINOPOISK_RATING = "kinopoisk_rating"
        const val KINOPOISK_RATING_VOTE_COUNT = "kinopoisk_rating_vote_count"

        const val UPDATED_AT = "updated_at"
    }
}