package io.github.artenes.clucko

data class ClockInItem(
    val timestamp: Time,
    val time: String,
    val isIn: Boolean
)