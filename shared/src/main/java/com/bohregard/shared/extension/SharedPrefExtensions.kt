@file:Suppress("UNCHECKED_CAST")

package com.bohregard.shared.extension

import android.content.SharedPreferences
import androidx.core.content.edit
import java.lang.Exception

fun <T: Any> SharedPreferences.get(key: String, defaultValue: T): T {
    return when (defaultValue) {
        is Boolean -> getBoolean(key, defaultValue) as T
        is String -> getString(key, defaultValue) as T
        is Int -> getInt(key, defaultValue) as T
        is Long -> getLong(key, defaultValue) as T
        is Float -> getFloat(key, defaultValue) as T
        else -> throw Exception("")
    }
}

fun <T> SharedPreferences.save(key: String, value: T) {
    edit {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
        }
    }
}