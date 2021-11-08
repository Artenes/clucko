package io.github.artenes.clucko

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditCurrentDayViewModel @Inject constructor(private val preferences: PreferencesRepository) :
    ViewModel() {

    private var now: Time

    private val days = mapOf(
        1 to 31,
        2 to 28,
        3 to 31,
        4 to 30,
        5 to 31,
        6 to 30,
        7 to 31,
        8 to 31,
        9 to 30,
        10 to 31,
        11 to 30,
        12 to 31
    )

    private val _displayDate = MutableLiveData<Time>()
    val displayDate: LiveData<Time>
        get() = _displayDate

    private val _dayMaxValue = MutableLiveData<Int>()
    val dayMaxValue: LiveData<Int>
        get() = _dayMaxValue

    private val _closeEvent = MutableLiveData<Event<Boolean>>()
    val closeEvent: LiveData<Event<Boolean>>
        get() = _closeEvent

    init {
        now = preferences.currentDate();
        updateMaxDay(now.month)
        _displayDate.value = now
    }

    fun setYear(year: Int) {
        now = now.setYear(year)
        _displayDate.value = now
    }

    fun setMonth(month: Int) {
        updateMaxDay(month)
        now = now.setMonth(month)
        _displayDate.value = now
    }

    fun setDay(day: Int) {
        now = now.setDay(day)
        _displayDate.value = now
    }

    fun save() {
        preferences.setCurrentDate(now)
        _closeEvent.value = Event(true)
    }

    private fun updateMaxDay(month: Int) {
        val newMaxDay = getMaxDay(month, now.year)
        if (now.day > newMaxDay) {
            now = now.setDay(newMaxDay)
        }
        _dayMaxValue.value = newMaxDay
    }

    private fun getMaxDay(month: Int, year: Int): Int {

        if (month == 2 && isLeap(year)) {
            return 29
        }

        return days[month] as Int
    }

    private fun isLeap(year: Int) = when {
        year % 4 == 0 -> {
            when {
                year % 100 == 0 -> year % 400 == 0
                else -> true
            }
        }
        else -> false
    }

}