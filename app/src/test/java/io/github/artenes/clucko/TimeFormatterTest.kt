package io.github.artenes.clucko

import io.github.artenes.clucko.core.TimeAmount
import io.github.artenes.clucko.core.TimeFormatter
import org.junit.Assert.*
import org.junit.Test

class TimeFormatterTest {

    @Test
    fun toHourMinute_withNegativeValue() {
        val amount = TimeAmount(-456)
        val result = TimeFormatter.toHourMinute(amount)
        assertEquals("-07:36", result)
    }

    @Test
    fun toHourMinute_withPositiveValue() {
        val amount = TimeAmount(456)
        val result = TimeFormatter.toHourMinute(amount)
        assertEquals("07:36", result)
    }

}