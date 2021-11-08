package io.github.artenes.clucko.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.artenes.clucko.core.Event
import io.github.artenes.clucko.core.HourTime
import io.github.artenes.clucko.database.PreferencesRepository
import javax.inject.Inject

@HiltViewModel
class EditHoursPerDayViewModel @Inject constructor(private val preferences: PreferencesRepository) :
    ViewModel() {

    private var hours: HourTime

    private val _hoursPerDay = MutableLiveData<HourTime>()
    val hoursPerDay: LiveData<HourTime>
        get() = _hoursPerDay

    private val _closeEvent = MutableLiveData<Event<Boolean>>()
    val closeEvent: LiveData<Event<Boolean>>
        get() = _closeEvent

    init {
        hours = preferences.minutesPerDay();
        _hoursPerDay.value = hours
    }

    fun setHour(hour: Int) {
        hours = HourTime(hour, hours.minute)
        _hoursPerDay.value = hours
    }

    fun setMinute(minute: Int) {
        hours = HourTime(hours.hour, minute)
        _hoursPerDay.value = hours
    }

    fun save() {
        preferences.setMinutesPerDay(hours)
        _closeEvent.value = Event(true)
    }

}