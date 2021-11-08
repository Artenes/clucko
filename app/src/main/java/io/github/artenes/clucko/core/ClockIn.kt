package io.github.artenes.clucko.core

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clockins")
data class ClockIn(
    @PrimaryKey val timestamp: Time
)