package io.github.artenes.clucko.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.artenes.clucko.core.ClockIn

@Database(entities = [ClockIn::class], version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clockInDao(): ClockInsDao
}