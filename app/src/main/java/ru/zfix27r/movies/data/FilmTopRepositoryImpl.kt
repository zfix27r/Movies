package ru.zfix27r.movies.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.zfix27r.movies.KINOPOISKAPIUNOFFICIAL_TECH_KEY
import ru.zfix27r.movies.data.film.FilmTop
import ru.zfix27r.movies.data.film.FilmTopFilm
import ru.zfix27r.movies.domain.FilmTopRepository
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class FilmTopRepositoryImpl @Inject constructor(): FilmTopRepository {
    override suspend fun getTopFilms(): Flow<List<FilmTopFilm>> {
        return flow {
            val url = URL(BASE_URL + TOP)
            val connection = url.openConnection() as HttpsURLConnection

            connection.requestMethod = "GET"
            connection.setRequestProperty("X-API-KEY", KINOPOISKAPIUNOFFICIAL_TECH_KEY)
            connection.setRequestProperty("User-Agent", HEADER_USER_AGENT)
            connection.setRequestProperty("Content-Type", HEADER_CONTENT_TYPE)

            val inputStream = connection.inputStream

            JsonReader(inputStream.reader()).use { jsonReader ->
                val type = object : TypeToken<FilmTop>() {}.type
                val list: FilmTop = Gson().fromJson(jsonReader, type)
                emit(list.films)
            }

            connection.disconnect()
        }
    }

    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films"
        const val TOP = "/top"

        const val HEADER_USER_AGENT = "zfix27r"
        const val HEADER_CONTENT_TYPE = "application/json"
    }
}