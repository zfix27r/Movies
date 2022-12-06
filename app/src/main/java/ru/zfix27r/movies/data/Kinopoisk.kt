package ru.zfix27r.movies.data

import android.util.JsonReader
import android.util.JsonToken
import ru.zfix27r.movies.data.FilmTopResponse
import java.io.InputStreamReader

class Kinopoisk(inputStreamReader: InputStreamReader) {
    private var reader: JsonReader

    init {
        reader = JsonReader(inputStreamReader)
    }

    private fun nextString(): String? {
        reader.nextName()
        return if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            null
        } else reader.nextString()
    }

    private fun readInt(): Int? {
        reader.nextName()
        return if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            null
        } else reader.nextInt()
    }

    private fun readLong(): Long? {
        reader.nextName()
        return if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            null
        } else reader.nextLong()
    }

    fun getTopFilms(): FilmTopResponse {
        reader.beginObject()
        val response = FilmTopResponse(
            pagesCount = readInt(),
            films = getFilms()
        )
        reader.endObject()
        reader.close()
        return response
    }

    private fun getFilms(): List<FilmTopResponse.Film> {
        val mutableList: MutableList<FilmTopResponse.Film> = mutableListOf()

        reader.nextName()
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            mutableList.add(
                FilmTopResponse.Film(
                    id = readLong(),
                    nameRu = nextString(),
                    nameEn = nextString(),
                    year = nextString(),
                    length = nextString(),
                    countries = getCountries(),
                    genres = getGenres(),
                    rating = nextString(),
                    ratingVoteCount = readInt(),
                    posterUrl = nextString(),
                    posterUrlPreview = nextString(),
                    ratingChange = nextString()
                )
            )
            reader.endObject()
        }
        reader.endArray()

        return mutableList
    }

    private fun getCountries(): List<FilmTopResponse.Film.Country> {
        val mutableList: MutableList<FilmTopResponse.Film.Country> = mutableListOf()

        reader.nextName()
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            mutableList.add(
                FilmTopResponse.Film.Country(
                    country = nextString()
                )
            )
            reader.endObject()
        }
        reader.endArray()

        return mutableList
    }

    private fun getGenres(): List<FilmTopResponse.Film.Genre> {
        val mutableList: MutableList<FilmTopResponse.Film.Genre> = mutableListOf()

        reader.nextName()
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            mutableList.add(
                FilmTopResponse.Film.Genre(
                    genre = nextString()
                )
            )
            reader.endObject()
        }
        reader.endArray()

        return mutableList
    }

    /*    private fun <T> parse(reader: JsonReader): JsonReader {
    while (reader.hasNext()) {
        when (reader.peek()) {
            JsonToken.BEGIN_OBJECT -> {
                reader.beginObject()
                parse<T>(reader)
            }
            JsonToken.BEGIN_ARRAY -> {
                reader.beginArray()
                parse<T>(reader)
            }
            JsonToken.END_OBJECT -> {
                reader.beginObject()
                break
            }
            JsonToken.END_ARRAY -> {
                reader.endArray()
                break
            }
            else -> parseCustom<T>()
        }
    }

    return reader
}

private fun <T> test(type: T): List<Any> {
    val mutableList: MutableList<Any> = mutableListOf()

    reader.beginArray()
    while (reader.hasNext()) {
        reader.beginObject()
        when (type) {
            is FilmTopResponse.Film.Genre -> mutableList.add(toGenre())
            is FilmTopResponse.Film.Country -> mutableList.add(toCountry())
            else -> {}
        }
        reader.endObject()
    }
    reader.endArray()

    return mutableList
}

private fun toCountry(): FilmTopResponse.Film.Country {
    return FilmTopResponse.Film.Country(
        country = readString()
    )
}

private fun toGenre(): FilmTopResponse.Film.Genre {
    return FilmTopResponse.Film.Genre(
        genre = readString()
    )
}*/

    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films"
        const val TOP = "/top"

        const val HEADER_USER_AGENT = "zfix27r"
        const val HEADER_CONTENT_TYPE = "application/json"
    }
}