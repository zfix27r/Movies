package ru.zfix27r.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.zfix27r.movies.data.local.entity.FilmEntity
import ru.zfix27r.movies.data.local.entity.TopEntity

@Database(
    entities = [
        FilmEntity::class,
        TopEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topDao(): TopDao
}