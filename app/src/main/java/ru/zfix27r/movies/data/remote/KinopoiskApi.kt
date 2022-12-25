package ru.zfix27r.movies.data.remote

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.zfix27r.movies.BuildConfig
import ru.zfix27r.movies.data.remote.model.KinopoiskTopResModel

interface KinopoiskApi {

    @Headers("X-API-KEY: ${BuildConfig.KINOPOISK_1_API_KEY}")
    @GET("v2.2/films/top")
    suspend fun getTop(
        @Query("page")
        page: Int
    ): KinopoiskTopResModel

    companion object {
        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"

        fun create(): KinopoiskApi {
            val client = OkHttpClient.Builder()
                .build()
            return Retrofit.Builder()
                .baseUrl(HttpUrl.parse(BASE_URL)!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(KinopoiskApi::class.java)
        }
    }
}