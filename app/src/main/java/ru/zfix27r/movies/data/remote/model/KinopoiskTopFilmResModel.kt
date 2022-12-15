package ru.zfix27r.movies.data.remote.model

import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopEntity
import java.time.LocalDateTime

data class KinopoiskTopFilmResModel(
    val filmId: Int,
    val nameRu: String,
    val nameEn: String,
    val year: String,
    val length: String,
    val countries: List<KinopoiskTopFilmCountryResModel>,
    val genres: List<KinopoiskTopFilmGenreResModel>,
    val rating: Float,
    val ratingVoteCount: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingChange: String
) {
    fun toTopEntity(id: Int): TopEntity {
        return TopEntity(
            id = id,
            filmId = filmId,
            updated_at = LocalDateTime.now().toString()
        )
    }

    fun toFilmEntity(): FilmEntity {
        return FilmEntity(
            nameRu = nameRu,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            kinopoiskId = filmId,
            kinopoiskRating = rating,
            kinopoiskRatingVoteCount = ratingVoteCount,
            updated_at = LocalDateTime.now().toString()
        )
    }
}