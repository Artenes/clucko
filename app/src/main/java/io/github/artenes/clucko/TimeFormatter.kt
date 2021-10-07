package io.github.artenes.clucko

import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TimeFormatter {

    companion object {

        fun toLocalDateFormat(time: Time) =
            Instant
                .ofEpochMilli(time.toEpochMilli())
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_LOCAL_DATE)

        fun toHourMinute(time: Time) =
            Instant
            .ofEpochMilli(time.toEpochMilli())
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("HH:mm"))

        fun toHourMinute(amount: TimeAmount) =
            LocalTime
                .ofSecondOfDay(amount.seconds)
                .format(DateTimeFormatter.ofPattern("HH:mm"))

    }

}