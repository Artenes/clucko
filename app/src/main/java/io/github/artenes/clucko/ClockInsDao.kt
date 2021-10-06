package io.github.artenes.clucko

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ClockInsDao {

    @Query("SELECT * FROM clockins")
    fun getAll(): List<Long>

    @Query("SELECT * FROM clockins WHERE :start <= timestamp AND timestamp <= :end")
    fun getInterval(start: Long, end: Long): Flow<List<Long>>

    @Insert
    suspend fun insert(timestamp: ClockIn)

}