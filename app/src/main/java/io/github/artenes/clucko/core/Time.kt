package io.github.artenes.clucko.core

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class Time(epochMilliseconds: Long = Instant.now().toEpochMilli()) {

    private val now = Instant.ofEpochMilli(epochMilliseconds)
    private val today = ZonedDateTime.ofInstant(now, ZoneId.systemDefault())

    val year: Int = today.year
    val month: Int = today.monthValue
    val day: Int = today.dayOfMonth
    val hour: Int = today.hour
    val minute: Int = today.minute

    fun toEpochMilli() = now.toEpochMilli()

    fun minutesInDay() = today.hour * 60 + today.minute

    fun setDay(day: Int): Time {
        val changedTime = ZonedDateTime.of(today.year, today.monthValue, day, today.hour, today.minute, today.second, today.nano, today.zone)
        val timestamp = changedTime.toInstant().toEpochMilli()
        return Time(timestamp)
    }

    fun setMonth(month: Int): Time {
        val changedTime = ZonedDateTime.of(today.year, month, today.dayOfMonth, today.hour, today.minute, today.second, today.nano, today.zone)
        val timestamp = changedTime.toInstant().toEpochMilli()
        return Time(timestamp)
    }

    fun setYear(year: Int): Time {
        val changedTime = ZonedDateTime.of(year, today.monthValue, today.dayOfMonth, today.hour, today.minute, today.second, today.nano, today.zone)
        val timestamp = changedTime.toInstant().toEpochMilli()
        return Time(timestamp)
    }

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

    fun addDays(days: Int): Time = Time(today.plusDays(days.toLong()).toInstant().toEpochMilli())

    fun minusDays(days: Int): Time = Time(today.minusDays(days.toLong()).toInstant().toEpochMilli())

    override fun toString(): String {
        return TimeFormatter.toReadableTime(this)
    }

    override fun equals(other: Any?): Boolean {
        return toEpochMilli() == (other as Time).toEpochMilli()
    }

    override fun hashCode(): Int {
        return now.toEpochMilli().hashCode()
    }

}