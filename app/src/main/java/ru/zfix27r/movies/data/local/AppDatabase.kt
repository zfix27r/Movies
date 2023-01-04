package ru.zfix27r.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.zfix27r.movies.data.local.entity.*

@Database(
    entities = [
        FilmEntity::class,
        TopBestEntity::class,
        TopPopularEntity::class,
        TopAwaitEntity::class,
        PremiereEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
}