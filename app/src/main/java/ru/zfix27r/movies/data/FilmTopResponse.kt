package ru.zfix27r.movies.data

data class FilmTopResponse(
    var pagesCount: Int? = null,
    var films: List<Film>? = null
) {
    data class Film(
        var id: Long? = null,
        var nameRu: String? = null,
        var nameEn: String? = null,
        var year: String? = null,
        var length: String? = null,
        var countries: List<Country>? = null,
        val genres: List<Genre>? = null,
        val rating: String? = null,
        val ratingVoteCount: Int? = null,
        val posterUrl: String? = null,
        val posterUrlPreview: String? = null,
        val ratingChange: String? = null
    ) {
        data class Country(
            val country: String? = null
        )

        data class Genre(
            var genre: String? = null
        )
    }
}