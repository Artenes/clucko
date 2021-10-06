package io.github.artenes.clucko

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Time(epochMilliseconds: Long = Instant.now().toEpochMilli()) {

    private val now = Instant.ofEpochMilli(epochMilliseconds)

    fun toEpochMilli() = now.toEpochMilli()

    fun getDayIntervalInMilli(): Pair<Long, Long> {
        val today = ZonedDateTime.ofInstant(now, ZoneId.systemDefault())
        val beginOfDay =
            ZonedDateTime.of(today.year, today.monthValue, today.dayOfMonth, 0, 0, 0, 0, today.zone)
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
        val startTimestamp = beginOfDay.toInstant().toEpochMilli()
        val endTimestamp = endOfDay.toInstant().toEpochMilli()
        return startTimestamp to endTimestamp
    }

    fun getReadableTime(): String =
        now.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("HH:mm"))

}