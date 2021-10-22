package io.github.artenes.clucko

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ClockInListModel(val scope: CoroutineScope, val now: Time, private val dao: ClockInsDao, private val preferences: PreferencesRepository) {

    val clockIns: LiveData<List<ClockInItem>>

    private val _balance = MutableLiveData<String>()
    val balance: LiveData<String>
        get() = _balance

    private val _left = MutableLiveData<String>()
    val left: LiveData<String>
        get() = _left

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    private val _editClockIn = MutableLiveData<Event<Long>>()
    val editClockIn: LiveData<Event<Long>>
        get() = _editClockIn

    init {
        _date.value = TimeFormatter.toLocalDateFormat(now)
        val liveData = dao.getIntervalAsFlow(now.startOfDay(), now.endOfDay())
            .onEach { list ->  updateBalance(list) }
            .map { list -> list.mapIndexed { index, clockIn -> ClockInItem(clockIn.timestamp, TimeFormatter.toHourMinute(clockIn.timestamp), index % 2 == 0) } }
            .asLiveData()
        clockIns = liveData
        listenForPreferenceChange()
    }

    private fun listenForPreferenceChange() {
        scope.launch {
            preferences.listenForMinutesPerDay().collect {
                updateBalanceFromDatabase()
            }
        }
    }

    private fun updateBalanceFromDatabase() {
        scope.launch(Dispatchers.IO) {
            val list = dao.getInterval(now.startOfDay(), now.endOfDay())
            withContext(Dispatchers.Main) {
                updateBalance(list)
            }
        }
    }

    private fun updateBalance(list: List<ClockIn>) {
        val balance = Balance(list, preferences.minutesPerDay().toMinutes())
        val amount = balance.currentBalance()
        val left = balance.timeLeft()
        _balance.value = TimeFormatter.toHourMinute(amount)
        _left.value = TimeFormatter.toHourMinute(left)
    }

    fun editClockIn(index: Int) {
        GlobalScope.launch {
            val list = dao.getInterval(now.startOfDay(), now.endOfDay())
            val clockIn = list[index]
            withContext(Dispatchers.Main) {
                _editClockIn.value = Event(clockIn.timestamp.toEpochMilli())
            }
        }
    }

}