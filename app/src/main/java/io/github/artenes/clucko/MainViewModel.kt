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

    lateinit var clockIns: LiveData<List<ClockInItem>>

    private val _balance = MutableLiveData<String>()
    val balance: LiveData<String>
    get() = _balance

    private val _left = MutableLiveData<String>()
    val left: LiveData<String>
        get() = _left

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    fun init(now: Time) {

        _date.value = TimeFormatter.toLocalDateFormat(now)
        val liveData = dao.getInterval(now.startOfDay(), now.endOfDay())
            .onEach { list ->  updateBalance(list) }
            .map { list -> list.map { ClockInItem(it.timestamp, TimeFormatter.toHourMinute(it.timestamp), it.isIn) } }
            .asLiveData()
        clockIns = liveData

    }

    fun putClockIn() {
        viewModelScope.launch {
            val now = Time()

            val lastClockIn = dao.getLastClockIn(now.startOfDay(), now.endOfDay())
            val isIn = lastClockIn?.isIn?.not() ?: true

            dao.insert(ClockIn(now, isIn))
        }
    }

    private fun updateBalance(list: List<ClockIn>) {
        val balance = Balance(list)
        val amount = balance.currentBalance()
        val left = balance.timeLeft()
        _balance.value = TimeFormatter.toHourMinute(amount)
        _left.value = TimeFormatter.toHourMinute(left)
    }

}