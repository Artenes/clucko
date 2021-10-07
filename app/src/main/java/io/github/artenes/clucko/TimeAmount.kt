package io.github.artenes.clucko

import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeAmount(private val milliAmount: Long) {

    fun inMinutes(): Long = milliAmount / 1000 / 60

    fun format(pattern: String): String {
        val seconds = milliAmount / 1000
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalTime.ofSecondOfDay(seconds).format(formatter)
    }

}