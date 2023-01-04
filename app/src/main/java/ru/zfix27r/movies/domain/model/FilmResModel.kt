package ru.zfix27r.movies.domain.model

data class FilmResModel(
    val id: Int,
    val nameRu: String,
    val posterUrl: String,
    val rating: String,
    val ratingVoteCount: String
)