package com.tans.tweather2.api.moshiadapter

import com.squareup.moshi.JsonReader
import io.reactivex.Maybe

fun getObjectJsonReaderByName(name: String, jsonReader: JsonReader)
        : Maybe<JsonReader> = Maybe.fromCallable {
    val jsonReaderCopy = jsonReader.peekJson()
    var resultJsonReader: JsonReader? = null
    jsonReaderCopy.beginObject()
    while (jsonReaderCopy.hasNext()) {
        if (jsonReaderCopy.nextName() == name) {
            resultJsonReader = jsonReaderCopy.peekJson()
        } else {
            jsonReaderCopy.skipValue()
        }
    }
    jsonReaderCopy.endObject()
    resultJsonReader
}

fun getIntByName(name: String, jsonReader: JsonReader)
        : Maybe<Int> = Maybe.fromCallable {
    val jsonReaderCopy = jsonReader.peekJson()
    var resultValue: Int? = null
    jsonReaderCopy.beginObject()
    while (jsonReaderCopy.hasNext()) {
        if (jsonReaderCopy.nextName() == name) {
            resultValue = jsonReaderCopy.nextInt()
        } else {
            jsonReaderCopy.skipValue()
        }
    }
    jsonReaderCopy.endObject()
    resultValue
}

fun getFloatByName(name: String, jsonReader: JsonReader)
        : Maybe<Float> = Maybe.fromCallable {
    val jsonReaderCopy = jsonReader.peekJson()
    var resultValue: Float? = null
    jsonReaderCopy.beginObject()
    while (jsonReaderCopy.hasNext()) {
        if (jsonReaderCopy.nextName() == name) {
            resultValue = jsonReaderCopy.nextDouble().toFloat()
        } else {
            jsonReaderCopy.skipValue()
        }
    }
    jsonReaderCopy.endObject()
    resultValue
}

fun getStringByName(name: String, jsonReader: JsonReader)
        : Maybe<String> = Maybe.fromCallable {
    val jsonReaderCopy = jsonReader.peekJson()
    var resultValue: String? = null
    jsonReaderCopy.beginObject()
    while (jsonReaderCopy.hasNext()) {
        if (jsonReaderCopy.nextName() == name) {
            resultValue = jsonReaderCopy.nextString()
        } else {
            jsonReaderCopy.skipValue()
        }
    }
    jsonReaderCopy.endObject()
    resultValue
}