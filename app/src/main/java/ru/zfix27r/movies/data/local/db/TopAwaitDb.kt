package ru.zfix27r.movies.data.local.db

import androidx.room.Embedded
import androidx.room.Relation
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopAwaitEntity
import ru.zfix27r.movies.domain.model.BaseResModel

data class TopAwaitDb(
    @Embedded
    val top: TopAwaitEntity,

    @Relation(
        entity = FilmEntity::class,
        parentColumn = TopAwaitEntity.FILM_ID_NAME,
        entityColumn = FilmEntity.KINOPOISK_ID
    )
    val film: FilmEntity
) {
    fun toTopResModel() = BaseResModel(
        id = film.kinopoiskId,
        nameRu = film.nameRu,
        posterUrlPreview = film.posterUrlPreview
    )
}