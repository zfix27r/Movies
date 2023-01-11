package ru.zfix27r.movies.domain

import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.domain.model.MainResModel

interface MainRepository {
    fun getData(items: Int, pages: Int): Flow<MainResModel>
}