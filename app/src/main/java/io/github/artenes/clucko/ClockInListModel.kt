package io.github.artenes.clucko

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ClockInListModel(val now: Time, dao: ClockInsDao) {

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

    init {
        _date.value = TimeFormatter.toLocalDateFormat(now)
        val liveData = dao.getInterval(now.startOfDay(), now.endOfDay())
            .onEach { list ->  updateBalance(list) }
            .map { list -> list.map { ClockInItem(it.timestamp, TimeFormatter.toHourMinute(it.timestamp), it.isIn) } }
            .asLiveData()
        clockIns = liveData
    }

    private fun updateBalance(list: List<ClockIn>) {
        val balance = Balance(list)
        val amount = balance.currentBalance()
        val left = balance.timeLeft()
        _balance.value = TimeFormatter.toHourMinute(amount)
        _left.value = TimeFormatter.toHourMinute(left)
    }

}