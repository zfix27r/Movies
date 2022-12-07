package ru.zfix27r.movies.data.film

data class FilmTopFilm(
    val id: Long,
    val nameRu: String,
    val nameEn: String,
    val year: String,
    val length: String,
    val countries: List<FilmTopCountry>,
    val genres: List<FilmTopGenre>,
    val rating: String,
    val ratingVoteCount: Int,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingChange: String
)