package ru.zfix27r.movies.ui.main

import ru.zfix27r.movies.domain.model.TopResModel

interface MovieActionListener {
    fun onViewDetail(topResModel: TopResModel)
}