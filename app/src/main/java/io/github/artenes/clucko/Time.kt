package io.github.artenes.clucko

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Time(epochMilliseconds: Long = Instant.now().toEpochMilli()) {

    private val now = Instant.ofEpochMilli(epochMilliseconds)

    fun toEpochMilli() = now.toEpochMilli()

    fun setTime(hour: Int, minute: Int, second: Int = 0): Time {
        val today = ZonedDateTime.ofInstant(now, ZoneId.systemDefault())
        val changedTime =
            ZonedDateTime.of(today.year, today.monthValue, today.dayOfMonth, hour, minute, second, 0, today.zone)
        val timestamp = changedTime.toInstant().toEpochMilli()
        return Time(timestamp)
    }

    fun startOfDay(): Time {
        return setTime(0, 0)
    }

    fun endOfDay(): Time {
        return setTime(23, 59, 59)
    }

    fun format(pattern: String): String =
        now.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern))

    override fun equals(other: Any?): Boolean {
        return toEpochMilli() == (other as Time).toEpochMilli()
    }

    override fun hashCode(): Int {
        return now.toEpochMilli().hashCode()
    }

}