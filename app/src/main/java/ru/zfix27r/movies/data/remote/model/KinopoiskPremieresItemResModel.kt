package ru.zfix27r.movies.data.remote.model

import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.PremiereEntity
import ru.zfix27r.movies.domain.model.PremiereResModel

data class KinopoiskPremieresItemResModel(
    val kinopoiskId: Int,
    val nameRu: String,
    val posterUrlPreview: String,
    val premiereRu: String
) {
    fun toPremiereEntity() = PremiereEntity(
        filmId = kinopoiskId,
        premiereRu = premiereRu
    )

    fun toFilmEntity() = FilmEntity(
        nameRu = nameRu,
        posterUrlPreview = posterUrlPreview,
        kinopoiskId = kinopoiskId,
        updated_at = 0L
    )

    fun toPremiereResModel() = PremiereResModel(
        id = kinopoiskId,
        nameRu = nameRu,
        posterUrlPreview = posterUrlPreview,
        premiereRu = premiereRu
    )
}