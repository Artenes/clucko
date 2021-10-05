package io.github.artenes.clucko

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clockins")
data class ClockIn(
    @PrimaryKey val timestamp: Long
)