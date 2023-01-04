package ru.zfix27r.movies.data.local.db

import androidx.room.Embedded
import androidx.room.Relation
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopPopularEntity
import ru.zfix27r.movies.domain.model.TopResModel

data class TopPopularDb(
    @Embedded
    val top: TopPopularEntity,

    @Relation(
        entity = FilmEntity::class,
        parentColumn = TopPopularEntity.FILM_ID_NAME,
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