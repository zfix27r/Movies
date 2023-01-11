package ru.zfix27r.movies.domain.model

data class MainResModel(
    val premieres: List<PremiereResModel>,
    val await: List<BaseResModel>,
    val popular: List<BaseResModel>,
    val best: List<BaseResModel>
)