package ru.zfix27r.movies.data.local.db

import androidx.room.Embedded
import androidx.room.Relation
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopEntity
import ru.zfix27r.movies.domain.model.TopResModel

data class TopDb(
    @Embedded
    val top: TopEntity,

    @Relation(
        entity = FilmEntity::class,
        parentColumn = TopEntity.FILM_ID,
        entityColumn = FilmEntity.KINOPOISK_ID
    )
    val film: FilmEntity
) {
    fun toTopResModel() = TopResModel(
        id = film.kinopoiskId,
        nameRu = film.nameRu,
        posterUrlPreview = film.posterUrlPreview
    )
}