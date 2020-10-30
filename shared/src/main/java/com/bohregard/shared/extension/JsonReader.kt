package com.bohregard.shared.extension

import com.bohregard.shared.util.toClass
import com.squareup.moshi.JsonReader

fun JsonReader.readObject(work: JsonReader.() -> Unit) {
    beginObject()
    while (hasNext()) {
        work()
    }
    endObject()
}

fun JsonReader.readArray(work: JsonReader.() -> Unit) {
    beginArray()
    while (hasNext()) {
        work()
    }
    endArray()
}

fun JsonReader.readArrayObject(work: JsonReader.() -> Unit) {
    readArray {
        readObject {
            work()
        }
    }
}

fun JsonReader.readObjectByName(name: String, work: JsonReader.() -> Unit) {
    readObject {
        when (nextName()) {
            name -> work()
            else -> skipValue()
        }
    }
}

inline fun <reified T> JsonReader.readArrayObject(
    list: MutableList<T>,
    crossinline work: JsonReader.(map: MutableMap<String, Any>) -> Unit
) {
    readArray {
        val map = mutableMapOf<String, Any>()
        readObject {
            work(map)
        }

        list.add(map.toClass())
    }
}