package ru.zfix27r.movies.data.remote.model

data class KinopoiskPremieresResModel(
    val total: Int,
    val items: List<KinopoiskPremieresItemResModel>
)