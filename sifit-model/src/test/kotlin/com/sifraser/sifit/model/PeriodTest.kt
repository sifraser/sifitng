package com.sifraser.sifit.model

import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class PeriodTest {

    @Test
    fun periodsTo() {
        val y20 = LocalDateTime.of(2020, 1, 1, 0, 0)
        val y21 = LocalDateTime.of(2021, 1, 1, 0, 0)
        val y22 = LocalDateTime.of(2022, 1, 1, 0, 0)
        val y23 = LocalDateTime.of(2023, 1, 1, 0, 0)
        assertEquals(listOf(y20, y21, y22), y20.periodsTo(y23, Period.Year))
    }
}