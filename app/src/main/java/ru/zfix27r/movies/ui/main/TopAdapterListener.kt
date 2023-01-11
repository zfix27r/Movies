package ru.zfix27r.movies.ui.main

import ru.zfix27r.movies.domain.model.BaseResModel

interface TopAdapterListener {
    fun navDetail(film: BaseResModel)
}