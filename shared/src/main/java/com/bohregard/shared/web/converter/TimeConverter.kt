package com.bohregard.shared.web.converter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object TimeConverter {

    private val ISO_8601_DATE_REGEX =
        "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.?\\d*Z".toRegex()

    @ToJson
    fun instantToString(instant: Instant): String {
        return instant.toString()
    }

    @FromJson
    fun stringToInstant(string: String): Instant {
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
            .toInstant(ZoneOffset.UTC)
    }
}