package com.tans.tweather2.api.moshiadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.util.*

object DateIntAdapter : JsonAdapter<Date>() {

    override fun fromJson(reader: JsonReader): Date? = Date(reader.nextLong() * 1000)

    override fun toJson(writer: JsonWriter, value: Date?) {
        writer.value(value?.let { it.time / 1000 })
    }

}