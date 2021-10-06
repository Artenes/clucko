package io.github.artenes.clucko

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: ClockInsDao,
) : ViewModel() {

    val clockIns: LiveData<List<ClockInItem>>

    init {

        val dayInterval = Time().getDayIntervalInMilli()
        val liveData = dao.getInterval(dayInterval.first, dayInterval.second).map { list ->
            list.map { ClockInItem(it.timestamp, Time(it.timestamp).getReadableTime(), it.isIn) }
        }.asLiveData()
        clockIns = liveData

    }

    fun putClockIn() {
        viewModelScope.launch {
            val now = Time()
            val time = now.toEpochMilli()
            val dayInterval = now.getDayIntervalInMilli()

            val lastClockIn = dao.getLastClockIn(dayInterval.first, dayInterval.second)
            val isIn = lastClockIn.isIn.not()

            dao.insert(ClockIn(time, isIn))
        }
    }

}