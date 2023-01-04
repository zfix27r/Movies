package ru.zfix27r.movies.data.local.db

import androidx.room.Embedded
import androidx.room.Relation
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.PremiereEntity
import ru.zfix27r.movies.domain.model.PremiereResModel

data class PremiereDb(
    @Embedded
    val premiere: PremiereEntity,

    @Relation(
        entity = FilmEntity::class,
        parentColumn = PremiereEntity.FILM_ID_NAME,
        entityColumn = FilmEntity.KINOPOISK_ID
    )
    val film: FilmEntity
) {
    fun toPremiereResModel() = PremiereResModel(
        id = film.kinopoiskId,
        nameRu = film.nameRu,
        posterUrlPreview = film.posterUrlPreview,
        premiereRu = premiere.premiereRu
    )
}