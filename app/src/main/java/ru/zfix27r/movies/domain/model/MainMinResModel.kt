package ru.zfix27r.movies.domain.model

data class MainMinResModel(
    val premieres: List<PremiereResModel>,
    val await: List<TopResModel>,
    val popular: List<TopResModel>,
    val best: List<TopResModel>
)