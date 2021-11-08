package io.github.artenes.clucko

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.*

class PreferencesRepository(context: Context): SharedPreferences.OnSharedPreferenceChangeListener {

    private val sharedPreferences =
        context.getSharedPreferences("clucko_preferences", Context.MODE_PRIVATE)

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private val _minutesPerDayFlow = MutableStateFlow(minutesPerDay())
    val minutesPerDayFlow: StateFlow<HourTime>
        get() = _minutesPerDayFlow

    private val _currentDateFlow = MutableStateFlow(currentDate())
    val currentDateFlow: StateFlow<Time>
        get() = _currentDateFlow

    fun minutesPerDay(): HourTime {
        val minutesDay = sharedPreferences.getInt("minutes_day", 8 * 60);
        val hours = minutesDay / 60
        val minutes = minutesDay - (hours * 60)
        return HourTime(hours, minutes)
    }

    fun currentDate(): Time {
        val currentDateMillis = sharedPreferences.getLong("current_date", Time().toEpochMilli())
        return Time(currentDateMillis)
    }

    fun setMinutesPerDay(hourTime: HourTime) {
        val editor = sharedPreferences.edit()
        editor.putInt("minutes_day", hourTime.toMinutes())
        editor.apply()
    }

    fun setCurrentDate(date: Time) {
        val editor = sharedPreferences.edit()
        editor.putLong("current_date", date.toEpochMilli())
        editor.apply()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        if (key == "minutes_day") {
            _minutesPerDayFlow.value = minutesPerDay()
            return
        }

        if (key == "current_date") {
            _currentDateFlow.value = currentDate()
            return
        }

    }
}