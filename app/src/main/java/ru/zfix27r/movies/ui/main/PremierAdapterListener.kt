package ru.zfix27r.movies.ui.main

import ru.zfix27r.movies.domain.model.PremiereResModel

interface PremierAdapterListener {
    fun navDetail(film: PremiereResModel)
}