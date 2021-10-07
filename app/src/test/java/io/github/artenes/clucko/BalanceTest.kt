package io.github.artenes.clucko

import org.junit.Assert.*

import org.junit.Test

class BalanceTest {

    @Test
    fun currentBalance_getsAmountForEvenList() {

        val clockIns = listOf(
            ClockIn(Time().setHour(8).setMinute(0), true),
            ClockIn(Time().setHour(12).setMinute(0), false),
            ClockIn(Time().setHour(13).setMinute(0), true),
            ClockIn(Time().setHour(16).setMinute(0), false)
        )

        val balance = Balance(clockIns)
        val amount = balance.currentBalance()

        assertEquals(420, amount.inMinutes())
        assertEquals("07:00", amount.format("HH:mm"))

    }

    @Test
    fun currentBalance_getsAmountForOddList() {

        val clockIns = listOf(
            ClockIn(Time().setHour(6).setMinute(30), true),
            ClockIn(Time().setHour(11).setMinute(45), false),
            ClockIn(Time().setHour(12).setMinute(55), true),
            ClockIn(Time().setHour(18).setMinute(23), false),
            ClockIn(Time().setHour(19).setMinute(12), false),
        )

        val balance = Balance(clockIns)
        val amount = balance.currentBalance()

        assertEquals(643, amount.inMinutes())
        assertEquals("10:43", amount.format("HH:mm"))

    }

}