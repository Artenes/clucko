package io.github.artenes.clucko

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClockInsDao {

    @Query("SELECT * FROM clockins")
    fun getAll(): List<Long>

    @Insert
    fun insert(timestamp: ClockIn)

}