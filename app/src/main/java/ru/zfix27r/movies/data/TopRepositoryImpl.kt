package ru.zfix27r.movies.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.zfix27r.movies.KINOPOISKAPIUNOFFICIAL_TECH_KEY_2
import ru.zfix27r.movies.data.local.TopDao
import ru.zfix27r.movies.data.remote.model.film.top.Top
import ru.zfix27r.movies.domain.TopRepository
import ru.zfix27r.movies.domain.common.ResponseThrow
import ru.zfix27r.movies.domain.common.ResponseType
import java.net.URL
import java.time.LocalDateTime
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class TopRepositoryImpl @Inject constructor(
    private val dao: TopDao
) :
    TopRepository {

    private var isOnceLoaded = false

    override suspend fun getTop() = getTopLocal().map { r -> r.map { it.toTopResModel() } }

    private suspend fun getTopLocal() = dao.getTop().onEach {
        getTopRemote()
        if (it.isEmpty() || isOutDate(it[0].top.updated_at)) getTopRemote()
    }

    private fun isOutDate(updateAt: String): Boolean {
        val now = LocalDateTime.now()
        val date = LocalDateTime.parse(updateAt)
        return now.minusDays(1L).isAfter(date)
    }

    private suspend fun getTopRemote() {
        //FIXME  без блокировки происходит повторная загрузка
        if (isOnceLoaded) return
        isOnceLoaded = true

        var connection: HttpsURLConnection? = null

        try {
            var id = 1L
            var page = 1
            var maxPage: Int

            while (true) {
                val url = URL("$BASE_URL$TOP?$PAGE=$page")
                connection = url.openConnection() as HttpsURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("X-API-KEY", KINOPOISKAPIUNOFFICIAL_TECH_KEY_2)
                connection.setRequestProperty("User-Agent", HEADER_USER_AGENT)
                connection.setRequestProperty("Content-Type", HEADER_CONTENT_TYPE)


                val inputStream = connection.inputStream

                val jsonReader = JsonReader(inputStream.reader())
                val type = object : TypeToken<Top>() {}.type
                val top: Top = Gson().fromJson(jsonReader, type)
                maxPage = top.pagesCount
                dao.saveFilmList(top.films.map { it.toFilmEntity() })
                dao.saveTopList(top.films.map { it.toTopEntity(id++) })

                connection.disconnect()

                if (maxPage == 0 || page == maxPage) break

                page++
            }
        } catch (e: Exception) {
            connection?.let {
                if (connection.responseCode == 402)
                    throw ResponseThrow(ResponseType.NO_INTERNET)
            }
            throw e
        }
    }

    override suspend fun getFilm(id: Long) = flow { emit(dao.getFilm(id).toFilmResModel()) }

    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films"
        const val TOP = "/top"
        const val PAGE = "page"

        const val HEADER_USER_AGENT = "zfix27r"
        const val HEADER_CONTENT_TYPE = "application/json"
    }
}