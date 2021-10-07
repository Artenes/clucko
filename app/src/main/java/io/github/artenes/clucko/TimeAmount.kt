package io.github.artenes.clucko

import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeAmount(val minutes: Long) {

    fun format(pattern: String): String {
        val seconds = minutes * 60
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalTime.ofSecondOfDay(seconds).format(formatter)
    }

}