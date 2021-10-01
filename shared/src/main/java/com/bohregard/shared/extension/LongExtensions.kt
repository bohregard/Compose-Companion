package com.bohregard.shared.extension

import java.util.concurrent.TimeUnit

fun Long.toHourMinuteSecondString(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this).toInt() % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this).toInt() % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this).toInt() % 60
    return when {
        hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
        minutes > 0 -> String.format("%02d:%02d", minutes, seconds)
        seconds > 0 -> String.format("00:%02d", seconds)
        else -> {
            "00:00"
        }
    }
}