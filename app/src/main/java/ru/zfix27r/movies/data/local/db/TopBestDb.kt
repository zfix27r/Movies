package ru.zfix27r.movies.data.local.db

import androidx.room.Embedded
import androidx.room.Relation
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopBestEntity
import ru.zfix27r.movies.domain.model.BaseResModel

data class TopBestDb(
    @Embedded
    val top: TopBestEntity,

    @Relation(
        entity = FilmEntity::class,
        parentColumn = TopBestEntity.FILM_ID_NAME,
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