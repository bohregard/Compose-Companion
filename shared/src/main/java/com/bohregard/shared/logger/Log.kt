package com.bohregard.shared.logger

import android.util.Log

fun <T> debug(message: T) {
    Log.d("DEBUG", message.toString())
}
