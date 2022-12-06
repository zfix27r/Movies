package ru.zfix27r.movies.ui.main

import ru.zfix27r.movies.data.FilmTopResponse

interface MovieActionListener {
    fun onViewDetail(film: FilmTopResponse.Film)
}