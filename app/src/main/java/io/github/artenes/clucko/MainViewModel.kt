package io.github.artenes.clucko

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: ClockInsDao,
) : ViewModel() {

    val clockIns: LiveData<List<String>>

    init {

        val dayInterval = Time().getDayIntervalInMilli()
        val liveData = dao.getInterval(dayInterval.first, dayInterval.second).map { list ->
            list.map { Time(it).getReadableTime() }
        }.asLiveData()
        clockIns = liveData

    }

    fun putClockIn() {
        val date = Instant.now()
        dao.insert(ClockIn(date.toEpochMilli()))
    }

}