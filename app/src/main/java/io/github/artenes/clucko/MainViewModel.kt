package io.github.artenes.clucko

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: ClockInsDao,
    private val preferences: PreferencesRepository
) : ViewModel() {

    private val days = mutableListOf<ClockInListModel>()

    val daysCount: Int
    get() = days.size

    var currentIndex: Int

    //https://www.strv.com/blog/how-to-set-up-dagger-viewmodel-saved-state-module-engineering
    init {
        val lastSevenDays = Time().minusDays(6)
        for(dayCount in 0..6) {
            val day = lastSevenDays.addDays(dayCount)
            val itemModel = ClockInListModel(viewModelScope, day, dao, preferences)
            days.add(itemModel)
        }
        currentIndex = days.size - 1
    }

    fun getItem(index: Int): ClockInListModel = days[index]

    fun putClockIn() {
        viewModelScope.launch {
            val currentDay = days[currentIndex].now
            val now = Time().setYear(currentDay.year).setMonth(currentDay.month).setDay(currentDay.day)

            dao.insert(ClockIn(now))
        }
    }

}