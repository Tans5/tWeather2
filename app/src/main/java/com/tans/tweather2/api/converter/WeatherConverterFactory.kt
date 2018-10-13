package com.tans.tweather2.api.converter

import com.google.gson.Gson
import com.tans.tweather2.api.ApiResponse
import com.tans.tweather2.entites.Atmosphere
import com.tans.tweather2.entites.Condition
import com.tans.tweather2.entites.Forecast
import com.tans.tweather2.entites.Wind
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object WeatherConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return if(getRawType(type) == ApiResponse::class.java) {
            when (getParameterUpperBound(0, type as ParameterizedType)) {
                Atmosphere::class.java -> AtmosphereConverter
                Forecast::class.java -> ForecastConverter
                Wind::class.java -> WindConverter
                Condition::class.java -> ConditionConverter
                else -> super.responseBodyConverter(type, annotations, retrofit)
            }
        } else {
            super.responseBodyConverter(type, annotations, retrofit)
        }
    }
}

class JSONObjectAdapter(private val jsonObject: JSONObject) {
    fun get(name: String): JSONObjectAdapter {
        println(name)
        return JSONObjectAdapter(jsonObject.get(name) as JSONObject)
    }

    override fun toString() = jsonObject.toString()
}

object AtmosphereConverter : Converter<ResponseBody, ApiResponse<Atmosphere>> {
    override fun convert(value: ResponseBody): ApiResponse<Atmosphere> {
        val resultString = JSONObjectAdapter(JSONObject(value.string()))
                .get("query")
                .get("results")
                .get("channel")
                .get("atmosphere")
                .toString()
        val body = Gson().fromJson<Atmosphere>(resultString, Atmosphere::class.java)
        return if (body == null) {
            ApiResponse.ApiEmptyResponse()
        } else {
            ApiResponse.ApiSuccessResponse(body)
        }
    }
}

object ConditionConverter : Converter<ResponseBody, ApiResponse<Condition>> {
    override fun convert(value: ResponseBody): ApiResponse<Condition> {
        val resultString = JSONObjectAdapter(JSONObject(value.string()))
                .get("query")
                .get("results")
                .get("channel")
                .get("item")
                .get("condition")
                .toString()
        val body = Gson().fromJson<Condition>(resultString, Condition::class.java)
        return if (body == null) {
            ApiResponse.ApiEmptyResponse()
        } else {
            ApiResponse.ApiSuccessResponse(body)
        }
    }

}

object ForecastConverter : Converter<ResponseBody, ApiResponse<Forecast>> {
    override fun convert(value: ResponseBody): ApiResponse<Forecast> {
        val resultString = JSONObjectAdapter(JSONObject(value.string()))
                .get("query")
                .get("results")
                .toString()
        val body = Gson().fromJson<Forecast>(resultString, Forecast::class.java)
        return if (body == null) {
            ApiResponse.ApiEmptyResponse()
        } else {
            ApiResponse.ApiSuccessResponse(body)
        }
    }

}

object WindConverter : Converter<ResponseBody, ApiResponse<Wind>> {
    override fun convert(value: ResponseBody): ApiResponse<Wind> {
        val resultString = JSONObjectAdapter(JSONObject(value.string()))
                .get("query")
                .get("results")
                .get("channel")
                .get("wind")
                .toString()
        val body = Gson().fromJson<Wind>(resultString, Wind::class.java)
        return if (body == null) {
            ApiResponse.ApiEmptyResponse()
        } else {
            ApiResponse.ApiSuccessResponse(body)
        }
    }

}