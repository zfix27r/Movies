package ru.zfix27r.movies.data.remote.model

import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopAwaitEntity
import ru.zfix27r.movies.data.local.entity.TopBestEntity
import ru.zfix27r.movies.data.local.entity.TopPopularEntity
import ru.zfix27r.movies.domain.model.TopResModel

data class KinopoiskTopFilmResModel(
    val filmId: Int,
    val nameRu: String,
    val rating: String,
    val posterUrlPreview: String
) {
    fun toTopBestEntity() = TopBestEntity(
        filmId = filmId
    )

    fun toTopPopularEntity() = TopPopularEntity(
        filmId = filmId
    )

    fun toTopAwaitEntity() = TopAwaitEntity(
        filmId = filmId
    )

    fun toFilmEntity() = FilmEntity(
        nameRu = nameRu,
        posterUrlPreview = posterUrlPreview,
        kinopoiskId = filmId,
        kinopoiskRating = rating,
        updated_at = 0L
    )

    fun toTopResModel() = TopResModel(
        id = filmId,
        nameRu = nameRu,
        posterUrlPreview = posterUrlPreview
    )
}