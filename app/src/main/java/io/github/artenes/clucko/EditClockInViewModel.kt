package io.github.artenes.clucko

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditClockInViewModel @Inject constructor(private val dao: ClockInsDao): ViewModel() {

    private lateinit var time: Time

    private val _timeText = MutableLiveData<String>()
    val timeText: LiveData<String>
    get() = _timeText

    fun init(timestamp: Long) {
        time = Time(timestamp)
        _timeText.value = TimeFormatter.toHourMinute(time)
    }

}