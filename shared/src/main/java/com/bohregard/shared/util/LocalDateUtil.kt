package com.bohregard.shared.util

import java.time.LocalDate
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

object LocalDateUtil {
    val weekStart: LocalDate
        get() = LocalDate.now()
            .with(TemporalAdjusters.previousOrSame(WeekFields.of(Locale.getDefault()).firstDayOfWeek))

    val weekEnd: LocalDate
        get() = weekStart.plusDays(7)
}