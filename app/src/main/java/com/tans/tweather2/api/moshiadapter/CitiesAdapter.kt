package com.tans.tweather2.api.moshiadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.tans.tweather2.api.service.Cities

object CitiesAdapter : JsonAdapter<Cities>() {

    override fun fromJson(reader: JsonReader): Cities {
        return emptyList()
    }

    override fun toJson(writer: JsonWriter, value: Cities?) {
    }

}