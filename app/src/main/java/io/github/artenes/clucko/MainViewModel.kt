package io.github.artenes.clucko

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: ClockInsDao,
) : ViewModel() {

    private val days = mutableListOf<ClockInListModel>()

    val daysCount: Int
    get() = days.size

    //https://www.strv.com/blog/how-to-set-up-dagger-viewmodel-saved-state-module-engineering
    init {
        val lastSevenDays = Time().minusDays(6)
        for(dayCount in 0..6) {
            val day = lastSevenDays.addDays(dayCount)
            val itemModel = ClockInListModel(day, dao)
            days.add(itemModel)
        }
    }

    fun getItem(index: Int): ClockInListModel = days[index]

    fun putClockIn() {
        viewModelScope.launch {
            val now = Time()

            val lastClockIn = dao.getLastClockIn(now.startOfDay(), now.endOfDay())
            val isIn = lastClockIn?.isIn?.not() ?: true

            dao.insert(ClockIn(now, isIn))
        }
    }

}