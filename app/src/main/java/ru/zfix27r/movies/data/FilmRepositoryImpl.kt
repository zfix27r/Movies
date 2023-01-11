package ru.zfix27r.movies.data

import kotlinx.coroutines.flow.Flow
import ru.zfix27r.movies.data.local.dao.MainDao
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.domain.FilmRepository
import ru.zfix27r.movies.domain.model.FilmResModel
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val dao: MainDao,
    private val api: KinopoiskApi
) :
    FilmRepository {
    override fun getFilm(id: Int): Flow<FilmResModel> {
        TODO("Not yet implemented")
    }
}