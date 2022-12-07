package ru.zfix27r.movies.ui.main

import ru.zfix27r.movies.data.film.FilmTopFilm

interface MovieActionListener {
    fun onViewDetail(film: FilmTopFilm)
}