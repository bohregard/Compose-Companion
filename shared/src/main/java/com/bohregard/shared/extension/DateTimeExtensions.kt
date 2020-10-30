package com.bohregard.shared.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toReadableString() = format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"))