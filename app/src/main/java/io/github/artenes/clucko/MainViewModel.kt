package io.github.artenes.clucko

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: ClockInsDao,
) : ViewModel() {

    val clockIns: LiveData<List<ClockInItem>>

    private val _balance = MutableLiveData<String>()
    val balance: LiveData<String>
    get() = _balance

    private val _left = MutableLiveData<String>()
    val left: LiveData<String>
        get() = _left

    init {

        val now = Time()
        val liveData = dao.getInterval(now.startOfDay(), now.endOfDay())
            .onEach { list ->  updateBalance(list) }
            .map { list -> list.map { ClockInItem(it.timestamp, it.timestamp.format("HH:mm"), it.isIn) } }
            .asLiveData()
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

    private fun updateBalance(list: List<ClockIn>) {

        val lastIndex = if (list.size % 2 == 0) list.size - 1 else list.size - 2

        var balance = 0L
        for (index in 0..lastIndex step 2) {
            val clockOut = list[index + 1].timestamp.toEpochMilli()
            val clockIn = list[index].timestamp.toEpochMilli()
            val worked = clockOut - clockIn
            balance += worked
        }

        val balanceInMinutes = balance / 1000 / 60
        _balance.value = balanceInMinutes.toString()

    }

}