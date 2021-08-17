package com.bohregard.shared.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun LocalDateTime.toReadableString() = format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"))

fun LocalDateTime.temporalString(): String {
    val time = toInstant(ZoneOffset.UTC).toEpochMilli()/1000
    val currentTime = Instant.now().toEpochMilli()/1000

    return when (val diff = currentTime - time) {
        in 0..60 -> "${diff}s"
        in 61..3600 -> "${diff/60}m"
        in 3601..86400 -> "${diff/3600}h"
        in 86401..604800 -> "${diff/86400}d"
        in 604801..2628000 -> "${diff/604800}w"
        in 2628001..31540000 -> "${diff/2628000}mo"
        else -> "${diff/31540000}y"
    }
}