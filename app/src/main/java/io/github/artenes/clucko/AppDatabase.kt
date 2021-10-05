package io.github.artenes.clucko

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ClockIn::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clockInDao(): ClockInsDao
}