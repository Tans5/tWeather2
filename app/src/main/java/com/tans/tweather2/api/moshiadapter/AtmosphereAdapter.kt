package com.tans.tweather2.api.moshiadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.tans.tweather2.entites.Atmosphere2

object AtmosphereAdapter : JsonAdapter<Atmosphere2>() {

    override fun fromJson(reader: JsonReader): Atmosphere2 {
        val resultReader = getObjectJsonReaderByName("query", reader)
                .flatMap { getObjectJsonReaderByName("results", it) }
                .flatMap { getObjectJsonReaderByName("channel", it) }
                .flatMap { getObjectJsonReaderByName("atmosphere", it) }
                .blockingGet()
        if (resultReader == null) {
            throw Throwable("Atmosphere2 is null.")
        } else {
            val humidity = getIntByName("humidity", resultReader).blockingGet()
            val pressure = getFloatByName("pressure", resultReader).blockingGet()
            val rising = getIntByName("rising", resultReader).blockingGet()
            val visibility = getFloatByName("visibility", resultReader).blockingGet()
            return Atmosphere2(humidity = humidity,
                    pressure = pressure,
                    rising = rising,
                    visibility = visibility)
        }
    }


    override fun toJson(writer: JsonWriter, value: Atmosphere2?) {
        writer.name("humidity")
        writer.value(value?.humidity)
        writer.name("pressure")
        writer.value(value?.pressure)
        writer.name("rising")
        writer.value(value?.rising)
        writer.name("visibility")
        writer.value(value?.visibility)
    }

}