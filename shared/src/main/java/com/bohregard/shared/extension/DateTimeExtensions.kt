package com.bohregard.shared.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toReadableString() = format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"))

const val SECOND = 60
const val MINUTE = 3600
const val HOUR = 86400
const val DAY = 604800
const val WEEK = 2628000
const val MONTH = 31540000

fun LocalDateTime.temporalString(): String {
    val time = toInstant(OffsetDateTime.now().offset).toEpochMilli()/1000
    val currentTime = Instant.now().toEpochMilli()/1000

    return when (val diff = currentTime - time) {
        in 0..SECOND -> "${diff}s"
        in SECOND + 1..MINUTE -> "${diff/60}m"
        in MINUTE + 1..HOUR -> "${diff/3600}h"
        in HOUR+ 1..DAY -> "${diff/86400}d"
        in DAY+ 1..WEEK -> "${diff/604800}w"
        in WEEK + 1..MONTH -> "${diff/2628000}mo"
        else -> "${diff/MONTH}y"
    }
}