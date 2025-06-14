package com.sifraser.sifit.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

enum class Period {
    Week,
    Month,
    Year;

    fun getDayNames(start: LocalDate): List<String> {
        val days = getDays(start)
        return when (this) {
            Week -> days.map { it.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.UK) }
            Month -> (1..31).map { "Day $it" }
            Year -> days.map {
                if (it == null) {
                    "29 February"
                } else {
                    "${it.dayOfMonth} ${it.month.getDisplayName(TextStyle.FULL, Locale.UK)}"
                }
            }
        }
    }

    fun getPeriodName(start: LocalDate) =
        when (this) {
            Week -> "w/c $start"
            Month -> if (start.dayOfMonth == 1) {
                start.month.getDisplayName(TextStyle.FULL, Locale.UK)
            } else {
                "${start.month.getDisplayName(TextStyle.SHORT, Locale.UK)}-${
                    start.plusMonths(1).month.getDisplayName(
                        TextStyle.SHORT,
                        Locale.UK
                    )
                }"
            }

            Year -> if (start.dayOfYear == 1) {
                "${start.year}"
            } else {
                "${start.year}-${start.year + 1 % 1000}"
            }
        }

    fun getDays(start: LocalDate) =
        when (this) {
            Week -> (0L..6).map { start.plusDays(it) }
            Month -> {
                val maxLength = start.month.maxLength()
                (0L..30).map { if (it <= maxLength) start.plusDays(it) else null }
            }

            // TODO - look at data table -- it's a day out wrt leap year
            Year -> {
                val year = if (start < LocalDate.of(start.year, 3, 1)) {
                    start.year
                } else {
                    start.year + 1
                }
                val include29Feb = java.time.Year.of(year).isLeap
                var decrementDay: Boolean? = null
                (0L..366).map {
                    val d = start.plusDays(it)
                    if (d.month == java.time.Month.FEBRUARY && d.dayOfMonth == 28 && !include29Feb) {
                        decrementDay = false
                    }
                    if (decrementDay == null) {
                        d
                    } else if (decrementDay == false) {
                        decrementDay = true
                        null
                    } else {
                        d.minusDays(1)
                    }
                }
            }
        }
}

fun LocalDateTime.inPeriod(start: LocalDateTime, type: Period) =
    isEqual(start) || (isAfter(start) && isBefore(start.plusPeriod(1, type)))

fun LocalDateTime.plusPeriod(count: Long, type: Period) =
    when (type) {
        Period.Week -> plusDays(count * 7L)
        Period.Month -> plusMonths(count)
        Period.Year -> plusYears(count)
    }

fun LocalDateTime.minusPeriod(count: Long, type: Period) =
    when (type) {
        Period.Week -> minusWeeks(count)
        Period.Month -> minusMonths(count)
        Period.Year -> minusYears(count)
    }

fun LocalDateTime.periodCount(end: LocalDateTime, type: Period): Int {
    val next = plusPeriod(1, type)
    return if (next < end) {
        1 + next.periodCount(end, type)
    } else {
        1
    }
}

// always includes final period that includes end
fun LocalDateTime.periodsTo(end: LocalDateTime, type: Period): List<LocalDateTime> {
    val next = plusPeriod(1, type)
    return if (next < end) {
        listOf(this) + next.periodsTo(end, type)
    } else {
        listOf(this)
    }
}

fun LocalDateTime.startOfCyPeriod(type: Period) =
    when (type) {
        Period.Week -> minusDays(dayOfWeek.ordinal.toLong())
        Period.Month -> withDayOfMonth(1).atStartOfDay()
        Period.Year -> withDayOfYear(1).atStartOfDay()
    }

fun LocalDateTime.atStartOfDay() = withHour(0).withMinute(0).withSecond(0).withNano(0)