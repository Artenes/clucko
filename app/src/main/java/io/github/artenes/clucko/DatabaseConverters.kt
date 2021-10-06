package io.github.artenes.clucko

import androidx.room.TypeConverter

class DatabaseConverters {

    @TypeConverter
    fun fromTimestamp(timestamp: Long): Time = Time(timestamp)

    @TypeConverter
    fun toTimestamp(time: Time): Long = time.toEpochMilli()

}