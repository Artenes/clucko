package io.github.artenes.clucko

data class ClockInItem(
    val timestamp: Long,
    val time: String,
    val isIn: Boolean
)