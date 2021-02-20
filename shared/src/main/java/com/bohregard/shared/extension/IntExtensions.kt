package com.bohregard.shared.extension

fun Long.toSizeString(): String {
    return when {
        this > 1024 * 1024 * 1024 -> {
            "%.2f".format(this/1024f/1024f/1024f) + "gb"
        }
        this > 1024 * 1024-> {
            "%.2f".format(this/1024f/1024f) + "mb"
        }
        this > 1024 -> {
            "%.2f".format(this/1024f) + "kb"
        }
        else -> {
            "${this}b"
        }
    }
}

fun Int.readableString(): String {
    return when {
        this > 999_999 -> {
            "${this/1_000_000}M"
        }
        this > 999 -> {
            "${this/1000}K"
        }
        else -> "$this"
    }
}