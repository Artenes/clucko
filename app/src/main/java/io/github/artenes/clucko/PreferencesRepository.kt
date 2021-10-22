package io.github.artenes.clucko

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PreferencesRepository(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("clucko_preferences", Context.MODE_PRIVATE)

    fun listenForMinutesPerDay(): Flow<HourTime> = callbackFlow {

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { pref, key ->
            if (key == "minutes_day") {
                trySend(minutesPerDay())
            }
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }

    }

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