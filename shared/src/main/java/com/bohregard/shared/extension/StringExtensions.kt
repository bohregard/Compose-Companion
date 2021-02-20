package com.bohregard.shared.extension

import java.util.*

fun String.toBase64(): String {
    return Base64.getEncoder().encodeToString(this.toByteArray())
}