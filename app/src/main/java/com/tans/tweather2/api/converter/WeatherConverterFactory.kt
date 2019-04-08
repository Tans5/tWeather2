package com.tans.tweather2.api.converter

import com.google.gson.Gson
import com.tans.tweather2.entites.Atmosphere
import com.tans.tweather2.entites.Condition
import com.tans.tweather2.entites.Forecast
import com.tans.tweather2.entites.Wind
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

object WeatherConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return when (getRawType(type)) {
                Atmosphere::class.java -> AtmosphereConverter
                Forecast::class.java -> ForecastConverter
                Wind::class.java -> WindConverter
                Condition::class.java -> ConditionConverter
                else -> super.responseBodyConverter(type, annotations, retrofit)
            }
    }
}

class JSONObjectAdapter(private val jsonObject: JSONObject) {
    fun get(name: String): JSONObjectAdapter = JSONObjectAdapter(jsonObject.get(name) as JSONObject)

    override fun toString() = jsonObject.toString()
}

object AtmosphereConverter : Converter<ResponseBody, Atmosphere> {
    override fun convert(value: ResponseBody): Atmosphere {
        return try {
            val resultString = JSONObjectAdapter(JSONObject(value.string()))
                    .get("query")
                    .get("results")
                    .get("channel")
                    .get("atmosphere")
                    .toString()
            Gson().fromJson<Atmosphere>(resultString, Atmosphere::class.java)
        } catch (e: Exception) {
            throw e
        }
    }
}

object ConditionConverter : Converter<ResponseBody, Condition> {
    override fun convert(value: ResponseBody): Condition {
        return try {
            val resultString = JSONObjectAdapter(JSONObject(value.string()))
                    .get("query")
                    .get("results")
                    .get("channel")
                    .get("item")
                    .get("condition")
                    .toString()
            Gson().fromJson<Condition>(resultString, Condition::class.java)
        } catch (e: Exception) {
            throw e
        }
    }

}

object ForecastConverter : Converter<ResponseBody, Forecast> {
    override fun convert(value: ResponseBody): Forecast {
        return try {
            val resultString = JSONObjectAdapter(JSONObject(value.string()))
                    .get("query")
                    .get("results")
                    .toString()
            Gson().fromJson<Forecast>(resultString, Forecast::class.java)
        } catch (e: Exception) {
            throw e
        }
    }

}

object WindConverter : Converter<ResponseBody, Wind> {
    override fun convert(value: ResponseBody): Wind {
        return try {
            val resultString = JSONObjectAdapter(JSONObject(value.string()))
                    .get("query")
                    .get("results")
                    .get("channel")
                    .get("wind")
                    .toString()
            Gson().fromJson<Wind>(resultString, Wind::class.java)
        }catch (e: Exception) {
            throw e
        }
    }

}