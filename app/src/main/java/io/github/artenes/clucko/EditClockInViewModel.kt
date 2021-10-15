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

    private lateinit var timeText: String

    private val _initialTimeText = MutableLiveData<String>()
    val initialTimeText: LiveData<String>
    get() = _initialTimeText

    private val _closeEvent = MutableLiveData<Event<Boolean>>()
    val closeEvent: LiveData<Event<Boolean>>
        get() = _closeEvent

    fun init(timestamp: Long) {
        time = Time(timestamp)
        _initialTimeText.value = TimeFormatter.toHourMinute(time)
    }

    fun setTime(time: String) {
        timeText = time
    }

    fun save() {
        val parts = timeText.split(":").map { it.toInt() }
        val hour = parts[0]
        val minutes = parts[1]
        val newTime = time.setHour(hour).setMinute(minutes)
        viewModelScope.launch {
            val clockIn = dao.getClockIn(time)
            dao.delete(clockIn)
            val newClockIn = ClockIn(newTime, clockIn.isIn)
            dao.insert(newClockIn)
            withContext(Dispatchers.Main) {
                _closeEvent.value = Event(true)
            }
        }
    }

}