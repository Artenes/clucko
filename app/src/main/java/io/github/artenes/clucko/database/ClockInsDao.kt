package io.github.artenes.clucko.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.artenes.clucko.core.Time
import io.github.artenes.clucko.core.ClockIn
import kotlinx.coroutines.flow.Flow

@Dao
interface ClockInsDao {

    @Query("SELECT * FROM clockins WHERE :start <= timestamp AND timestamp <= :end")
    fun getIntervalAsFlow(start: Time, end: Time): Flow<List<ClockIn>>

    @Query("SELECT * FROM clockins WHERE :start <= timestamp AND timestamp <= :end")
    fun getInterval(start: Time, end: Time): List<ClockIn>

    @Insert
    suspend fun insert(timestamp: ClockIn)

    @Query("SELECT * FROM clockins WHERE :start <= timestamp AND timestamp <= :end ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastClockIn(start: Time, end: Time): ClockIn?

    @Query("SELECT * FROM clockins WHERE timestamp == :timestamp")
    suspend fun getClockIn(timestamp: Time): ClockIn

    @Delete
    suspend fun delete(timestamp: ClockIn)

}