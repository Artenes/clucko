package io.github.artenes.clucko

import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TimeFormatter {

    companion object {

        fun toReadableTime(time: Time): String =
            Instant
            .ofEpochMilli(time.toEpochMilli())
            .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("d/M/y HH:mm"))

        fun toLocalDateFormat(time: Time): String =
            Instant
                .ofEpochMilli(time.toEpochMilli())
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_LOCAL_DATE)

        fun toHourMinute(time: Time): String =
            Instant
            .ofEpochMilli(time.toEpochMilli())
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("HH:mm"))

        fun toHourMinute(amount: TimeAmount): String =
            LocalTime
                .ofSecondOfDay(amount.seconds)
                .format(DateTimeFormatter.ofPattern("HH:mm"))

    }

}