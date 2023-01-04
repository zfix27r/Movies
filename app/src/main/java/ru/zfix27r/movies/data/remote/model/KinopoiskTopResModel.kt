package ru.zfix27r.movies.data.remote.model

data class KinopoiskTopResModel(
    val pagesCount: Int,
    val films: List<KinopoiskTopFilmResModel>
)