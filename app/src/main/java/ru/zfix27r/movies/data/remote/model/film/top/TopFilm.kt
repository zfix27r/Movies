package ru.zfix27r.movies.data.remote.model.film.top

import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopEntity
import java.time.LocalDateTime

data class TopFilm(
    val filmId: Long,
    val nameRu: String,
    val nameEn: String,
    val year: String,
    val length: String,
    val countries: List<TopCountry>,
    val genres: List<TopGenre>,
    val rating: Float,
    val ratingVoteCount: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingChange: String
) {
    fun toTopEntity(id: Long): TopEntity {
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