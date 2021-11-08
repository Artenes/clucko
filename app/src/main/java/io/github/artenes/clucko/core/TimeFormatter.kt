package io.github.artenes.clucko.core

import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))

        fun toHourMinute(time: Time): String =
            Instant
            .ofEpochMilli(time.toEpochMilli())
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("HH:mm"))

        fun toHourMinute(amount: TimeAmount): String {
            val isNegative = amount.seconds < 0
            val seconds = if (isNegative) amount.seconds * -1 else amount.seconds
            val format = if (isNegative) "-HH:mm" else "HH:mm"
            return LocalTime.ofSecondOfDay(seconds).format(DateTimeFormatter.ofPattern(format))
        }


    }

}