package io.github.artenes.clucko.core

data class ClockInItem(
    val timestamp: Time,
    val time: String,
    val isIn: Boolean
)