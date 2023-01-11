package ru.zfix27r.movies.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.zfix27r.movies.data.local.AppDatabase
import ru.zfix27r.movies.data.remote.KinopoiskApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val APP_DATABASE_NAME = "movies"
    private const val APP_DATABASE_DUMP_NAME = "movies.db"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java,
        APP_DATABASE_NAME
    )/*.createFromAsset(APP_DATABASE_DUMP_NAME)*/.build()

    @Singleton
    @Provides
    fun provideMainDao(db: AppDatabase) = db.mainDao()

    @Singleton
    @Provides
    fun providePremiereDao(db: AppDatabase) = db.premiereDao()

    @Singleton
    @Provides
    fun provideTopDao(db: AppDatabase) = db.topDao()

    @Singleton
    @Provides
    fun provideTopBestDao(db: AppDatabase) = db.topBestDao()

    @Singleton
    @Provides
    fun provideFilmDao(db: AppDatabase) = db.filmDao()

    @Singleton
    @Provides
    fun provideKinopoiskApi() = KinopoiskApi.create()
}