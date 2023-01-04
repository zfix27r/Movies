package ru.zfix27r.movies.data.remote

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.zfix27r.movies.BuildConfig
import ru.zfix27r.movies.data.remote.common.KinopoiskTopType
import ru.zfix27r.movies.data.remote.model.KinopoiskPremieresResModel
import ru.zfix27r.movies.data.remote.model.KinopoiskTopResModel
import java.time.LocalDateTime
import java.time.Month
import java.util.Date

interface KinopoiskApi {
    @Headers("X-API-KEY: ${BuildConfig.KINOPOISK_1_API_KEY}")
    @GET("v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: Month
    ): KinopoiskPremieresResModel

    @Headers("X-API-KEY: ${BuildConfig.KINOPOISK_1_API_KEY}")
    @GET("v2.1/films/search-by-keyword")
    suspend fun getFilmsByKeyword(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): KinopoiskTopResModel

    @Headers("X-API-KEY: ${BuildConfig.KINOPOISK_1_API_KEY}")
    @GET("v2.2/films/top")
    suspend fun getTop(
        @Query("type") type: String,
        @Query("page") page: Int
    ): KinopoiskTopResModel

    @Headers("X-API-KEY: ${BuildConfig.KINOPOISK_1_API_KEY}")
    @GET("v2.2/films/{id}")
    suspend fun getFilm(
        @Path("id") id: Int
    ): KinopoiskTopResModel

    companion object {
        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"

        fun create(): KinopoiskApi {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl(HttpUrl.parse(BASE_URL)!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(KinopoiskApi::class.java)
        }
    }
}