package io.github.artenes.clucko

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

}