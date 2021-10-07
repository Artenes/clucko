package io.github.artenes.clucko

import org.junit.Assert.*

import org.junit.Test

class BalanceTest {

    @Test
    fun currentBalance_getsAmountForEvenList() {

        val clockIns = listOf(
            ClockIn(Time().setTime(8, 0), true),
            ClockIn(Time().setTime(12, 0), false),
            ClockIn(Time().setTime(13, 0), true),
            ClockIn(Time().setTime(16, 0), false)
        )

        val balance = Balance(clockIns)
        val amount = balance.currentBalance()

        assertEquals(420, amount.inMinutes())
        assertEquals("07:00", amount.format("HH:mm"))

    }

    @Test
    fun currentBalance_getsAmountForOddList() {

        val clockIns = listOf(
            ClockIn(Time().setTime(6, 30), true),
            ClockIn(Time().setTime(11, 45), false),
            ClockIn(Time().setTime(12, 55), true),
            ClockIn(Time().setTime(18, 23), false),
            ClockIn(Time().setTime(19, 12), false)
        )

        val balance = Balance(clockIns)
        val amount = balance.currentBalance()

        assertEquals(643, amount.inMinutes())
        assertEquals("10:43", amount.format("HH:mm"))

    }

}