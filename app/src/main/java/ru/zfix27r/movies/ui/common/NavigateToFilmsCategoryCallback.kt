package ru.zfix27r.movies.ui.common

import ru.zfix27r.movies.ui.category.Categories

interface NavigateToFilmsCategoryCallback {
    fun toFilmsCategory(category: Categories)
}