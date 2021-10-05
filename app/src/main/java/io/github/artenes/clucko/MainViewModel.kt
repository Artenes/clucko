package io.github.artenes.clucko

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: AppDatabase
) : ViewModel() {

    fun putClockIn() {
        val date = Instant.now()
        db.clockInDao().insert(ClockIn(date.toEpochMilli()))
    }

    fun getTimes(): List<String> {
        val now = ZonedDateTime.now()
        val beginOfDay =
            ZonedDateTime.of(now.year, now.monthValue, now.dayOfMonth, 0, 0, 0, 0, now.zone)
        val endOfDay =
            ZonedDateTime.of(now.year, now.monthValue, now.dayOfMonth, 23, 59, 59, 0, now.zone)
        val startTimestamp = beginOfDay.toInstant().toEpochMilli()
        val endTimestamp = endOfDay.toInstant().toEpochMilli()

        return db.clockInDao().getInterval(startTimestamp, endTimestamp).map {
            Instant
                .ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }

}