package ru.zfix27r.movies.data.remote.model

data class KinopoiskTopResModel(
    var pagesCount: Int,
    var films: List<KinopoiskTopFilmResModel>
)