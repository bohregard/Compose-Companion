package com.bohregard.shared.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.lang.Exception

abstract class SharedPrefUtil(application: Application) {

    private val sharedPrefsName: String = "SharedPrefs"
    private val _sp: SharedPreferences = application.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)

    fun <T: Any> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is Boolean -> _sp.getBoolean(key, defaultValue) as T
            is String -> _sp.getString(key, defaultValue) as T
            is Int -> _sp.getInt(key, defaultValue) as T
            is Long -> _sp.getLong(key, defaultValue) as T
            is Float -> _sp.getFloat(key, defaultValue) as T
            else -> throw Exception("")
        }
    }

    fun <T> save(key: String, value: T) {
        _sp.edit {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
            }
        }
    }
}