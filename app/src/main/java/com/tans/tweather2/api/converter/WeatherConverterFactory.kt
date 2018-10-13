package com.tans.tweather2.api.converter

import com.google.gson.Gson
import com.tans.tweather2.entites.Atmosphere
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

object WeatherConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return when(type) {
            Atmosphere::class.java -> AtmosphereConverter
            else -> super.responseBodyConverter(type, annotations, retrofit)
        }
    }
}

class JSONObjectAdapter(val jsonObject: JSONObject) {
    fun get(name: String): JSONObjectAdapter =
            JSONObjectAdapter(jsonObject.get(name) as JSONObject)

    override fun toString() = jsonObject.toString()
}

object AtmosphereConverter : Converter<ResponseBody, Atmosphere> {
    override fun convert(value: ResponseBody): Atmosphere {
        println("${value.string()}")
        val resultString = JSONObjectAdapter(JSONObject(value.string()))
                .get("query")
                .get("results")
                .get("channel")
                .get("atmosphere")
                .toString()
        return Gson().fromJson<Atmosphere>(resultString, Atmosphere::class.java)
    }

}