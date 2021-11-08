package io.github.artenes.clucko.database

import androidx.room.TypeConverter
import io.github.artenes.clucko.core.Time

class DatabaseConverters {

    @TypeConverter
    fun fromTimestamp(timestamp: Long): Time = Time(timestamp)

    @TypeConverter
    fun toTimestamp(time: Time): Long = time.toEpochMilli()

}