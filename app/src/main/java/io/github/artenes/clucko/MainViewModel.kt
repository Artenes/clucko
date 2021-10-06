package io.github.artenes.clucko

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: ClockInsDao,
) : ViewModel() {

    val clockIns: LiveData<List<ClockInItem>>

    init {

        val now = Time()
        val liveData = dao.getInterval(now.startOfDay(), now.endOfDay()).map { list ->
            list.map { ClockInItem(it.timestamp, it.timestamp.format("HH:mm"), it.isIn) }
        }.asLiveData()
        clockIns = liveData

    }

    fun putClockIn() {
        viewModelScope.launch {
            val now = Time()

            val lastClockIn = dao.getLastClockIn(now.startOfDay(), now.endOfDay())
            val isIn = lastClockIn.isIn.not()

            dao.insert(ClockIn(now, isIn))
        }
    }

}