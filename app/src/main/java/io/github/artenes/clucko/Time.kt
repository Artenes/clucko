package io.github.artenes.clucko

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Time(epochMilliseconds: Long = Instant.now().toEpochMilli()) {

    private val now = Instant.ofEpochMilli(epochMilliseconds)
    private val today = ZonedDateTime.ofInstant(now, ZoneId.systemDefault())

    fun toEpochMilli() = now.toEpochMilli()

    fun minutesInDay() = today.hour * 60 + today.minute

    fun setHour(hour: Int): Time {
        val changedTime = ZonedDateTime.of(today.year, today.monthValue, today.dayOfMonth, hour, today.minute, today.second, today.nano, today.zone)
        val timestamp = changedTime.toInstant().toEpochMilli()
        return Time(timestamp)
    }

    fun setMinute(minute: Int): Time {
        val changedTime = ZonedDateTime.of(today.year, today.monthValue, today.dayOfMonth, today.hour, minute, today.second, today.nano, today.zone)
        val timestamp = changedTime.toInstant().toEpochMilli()
        return Time(timestamp)
    }

    fun setSecond(second: Int): Time {
        val changedTime = ZonedDateTime.of(today.year, today.monthValue, today.dayOfMonth, today.hour, today.minute, second, today.nano, today.zone)
        val timestamp = changedTime.toInstant().toEpochMilli()
        return Time(timestamp)
    }

    fun startOfDay(): Time {
        return setHour(0).setMinute(0)
    }

    fun endOfDay(): Time {
        return setHour(23).setMinute(59).setSecond(59)
    }

    override fun equals(other: Any?): Boolean {
        return toEpochMilli() == (other as Time).toEpochMilli()
    }

    override fun hashCode(): Int {
        return now.toEpochMilli().hashCode()
    }

}