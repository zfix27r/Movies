package ru.zfix27r.movies.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.zfix27r.movies.data.local.AppDatabase
import ru.zfix27r.movies.data.local.TopDao
import ru.zfix27r.movies.data.remote.KinopoiskApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val APP_DATABASE_NAME = "movies"
    private const val APP_DATABASE_DUMP_NAME = "movies.db"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            APP_DATABASE_NAME
        )/*.createFromAsset(APP_DATABASE_DUMP_NAME)*/.build()
    }

    @Singleton
    @Provides
    fun provideTopDao(db: AppDatabase): TopDao {
        return db.topDao()
    }

    @Singleton
    @Provides
    fun provideKinopoiskApi(): KinopoiskApi {
        return KinopoiskApi.create()
    }
}