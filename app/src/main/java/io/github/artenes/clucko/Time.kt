package io.github.artenes.clucko

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Time(epochMilliseconds: Long = Instant.now().toEpochMilli()) {

    private val now = Instant.ofEpochMilli(epochMilliseconds)

    fun toEpochMilli() = now.toEpochMilli()

    fun startOfDay(): Time {
        val today = ZonedDateTime.ofInstant(now, ZoneId.systemDefault())
        val beginOfDay =
            ZonedDateTime.of(today.year, today.monthValue, today.dayOfMonth, 0, 0, 0, 0, today.zone)
        val startTimestamp = beginOfDay.toInstant().toEpochMilli()
        return Time(startTimestamp)
    }

    fun endOfDay(): Time {
        val today = ZonedDateTime.ofInstant(now, ZoneId.systemDefault())
        val endOfDay =
            ZonedDateTime.of(
                today.year,
                today.monthValue,
                today.dayOfMonth,
                23,
                59,
                59,
                0,
                today.zone
            )
        val endTimestamp = endOfDay.toInstant().toEpochMilli()
        return Time(endTimestamp)
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