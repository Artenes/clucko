package io.github.artenes.clucko

class Balance(private val list: List<ClockIn>) {

    fun currentBalance(): TimeAmount {

        val lastIndex = if (list.size % 2 == 0) list.size - 1 else list.size - 2

        var balance = 0L
        for (index in 0..lastIndex step 2) {
            val clockOut = list[index + 1].timestamp.minutesInDay()
            val clockIn = list[index].timestamp.minutesInDay()
            val worked = clockOut - clockIn
            balance += worked
        }

        return TimeAmount(balance)

    }

}