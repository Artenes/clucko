package io.github.artenes.clucko

import java.time.ZonedDateTime

class Clock {

    companion object {

        fun getDayIntervalInMilli(): Pair<Long, Long> {
            val now = ZonedDateTime.now()
            val beginOfDay =
                ZonedDateTime.of(now.year, now.monthValue, now.dayOfMonth, 0, 0, 0, 0, now.zone)
            val endOfDay =
                ZonedDateTime.of(now.year, now.monthValue, now.dayOfMonth, 23, 59, 59, 0, now.zone)
            val startTimestamp = beginOfDay.toInstant().toEpochMilli()
            val endTimestamp = endOfDay.toInstant().toEpochMilli()
            return startTimestamp to endTimestamp
        }

    }

}