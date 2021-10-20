package io.github.artenes.clucko

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditClockInViewModel @Inject constructor(private val dao: ClockInsDao): ViewModel() {

    private lateinit var time: Time

    private lateinit var hourTime: HourTime

    private val _hourTimeLiveData = MutableLiveData<HourTime>()
    val hourTimeLiveData: LiveData<HourTime>
    get() = _hourTimeLiveData

    private val _closeEvent = MutableLiveData<Event<Boolean>>()
    val closeEvent: LiveData<Event<Boolean>>
        get() = _closeEvent

    fun init(timestamp: Long) {
        time = Time(timestamp)
        hourTime = HourTime(time.hour, time.minute)
        _hourTimeLiveData.value = hourTime
    }

    fun setHour(hour: Int) {
        hourTime = HourTime(hour, hourTime.minute)
        _hourTimeLiveData.value = hourTime
    }

    fun setMinute(minute: Int) {
        hourTime = HourTime(hourTime.hour, minute)
        _hourTimeLiveData.value = hourTime
    }

    fun save() {
        val newTime = time.setHour(hourTime.hour).setMinute(hourTime.minute)
        viewModelScope.launch {
            val clockIn = dao.getClockIn(time)
            dao.delete(clockIn)
            val newClockIn = ClockIn(newTime)
            dao.insert(newClockIn)
            closeView()
        }
    }

    fun delete() {
        viewModelScope.launch {
            val clockIn = dao.getClockIn(time)
            dao.delete(clockIn)
            closeView()
        }
    }

    private suspend fun closeView() {
        withContext(Dispatchers.Main) {
            _closeEvent.value = Event(true)
        }
    }

}