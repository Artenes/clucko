package io.github.artenes.clucko

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: ClockInsDao,
    private val preferences: PreferencesRepository
) : ViewModel() {

    private var now: Time;

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

    //https://www.strv.com/blog/how-to-set-up-dagger-viewmodel-saved-state-module-engineering
    init {
        now = Time()
        _date.value = TimeFormatter.toLocalDateFormat(now)
        val liveData = dao.getIntervalAsFlow(now.startOfDay(), now.endOfDay())
            .onEach { list ->  updateBalance(list) }
            .map { list -> list.mapIndexed { index, clockIn -> ClockInItem(clockIn.timestamp, TimeFormatter.toHourMinute(clockIn.timestamp), index % 2 == 0) } }
            .asLiveData()
        clockIns = liveData
        listenForPreferenceChange()
    }

    private fun listenForPreferenceChange() {
        viewModelScope.launch {
            preferences.listenForMinutesPerDay().collect {
                updateBalanceFromDatabase()
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

    private fun updateBalanceFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getInterval(now.startOfDay(), now.endOfDay())
            withContext(Dispatchers.Main) {
                updateBalance(list)
            }
        }
    }

    fun putClockIn() {
        viewModelScope.launch {
            val nowToInsert = Time().setYear(now.year).setMonth(now.month).setDay(now.day)
            dao.insert(ClockIn(nowToInsert))
        }
    }

    fun editClockIn(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getInterval(now.startOfDay(), now.endOfDay())
            val clockIn = list[index]
            withContext(Dispatchers.Main) {
                _editClockIn.value = Event(clockIn.timestamp.toEpochMilli())
            }
        }
    }

}