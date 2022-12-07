package ru.zfix27r.movies.domain

import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.data.film.FilmTopFilm

interface FilmTopRepository {
    suspend fun getTopFilms(): Flow<List<FilmTopFilm>>
}