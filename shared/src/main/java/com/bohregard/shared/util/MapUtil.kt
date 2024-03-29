package com.bohregard.shared.util

import com.bohregard.shared.logger.debug
import java.time.Instant
import kotlin.reflect.full.createType

inline fun <reified T> MutableMap<String, Any?>.toClass(): T {
    val params = T::class.constructors.first().parameters.map {
        if(this[it.name] == null) {
            when (it.type) {
                String::class.createType() -> ""
                Int::class.createType() -> 0
                Instant::class.createType(nullable = true) -> null
                else -> ""
            }
        } else {
            this[it.name]
        }
    }.toTypedArray()
    return T::class.java.constructors.first().newInstance(*params) as T
}