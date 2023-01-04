package ru.zfix27r.movies.data.remote.model

data class KinopoiskFilmResModel(
    val filmId: Int,
    val nameRu: String,
    val rating: Float,
    val posterUrl: String
)