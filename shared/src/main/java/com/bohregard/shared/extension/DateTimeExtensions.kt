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
        in 0..60 -> "${diff}sec ago"
        in 61..3600 -> "${diff/60}min ago "
        in 3601..86400 -> "${diff/3600}h ago "
        in 86401..345600 -> "${diff/86400}d ago "
        in 345601..4147200 -> "${diff/345600}w ago "
        else -> "${diff/4147200}m ago "
    }
}