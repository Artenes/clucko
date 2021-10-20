package io.github.artenes.clucko

import android.content.Context

class PreferencesRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("clucko_preferences", Context.MODE_PRIVATE)

    fun minutesPerDay(): HourTime {
        val minutesDay = sharedPreferences.getInt("minutes_day", 8 * 60);
        val hours = minutesDay / 60
        val minutes = minutesDay - (hours * 60)
        return HourTime(hours, minutes)
    }

    fun setMinutesPerDay(hourTime: HourTime) {
        val editor = sharedPreferences.edit()
        editor.putInt("minutes_day", hourTime.toMinutes())
        editor.apply()
    }

}