//package com.tans.tweather2.api.converter
//
//import com.google.gson.Gson
//import com.tans.tweather2.entites.Forecast2
//import okhttp3.ResponseBody
//import org.json.JSONObject
//import retrofit2.Converter
//import retrofit2.Retrofit
//import java.lang.reflect.Type
//
//object WeatherConverterFactory : Converter.Factory() {
//    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
//        return when (getRawType(type)) {
//                Atmosphere2::class.java -> AtmosphereConverter
//                Forecast2::class.java -> ForecastConverter
//                Wind2::class.java -> WindConverter
//                Condition2::class.java -> ConditionConverter
//                else -> super.responseBodyConverter(type, annotations, retrofit)
//            }
//    }
//}
//
//class JSONObjectAdapter(private val jsonObject: JSONObject) {
//    fun get(name: String): JSONObjectAdapter = JSONObjectAdapter(jsonObject.get(name) as JSONObject)
//
//    override fun toString() = jsonObject.toString()
//}
//
//object AtmosphereConverter : Converter<ResponseBody, Atmosphere2> {
//    override fun convert(value: ResponseBody): Atmosphere2 {
//        return try {
//            val resultString = JSONObjectAdapter(JSONObject(value.string()))
//                    .get("query")
//                    .get("results")
//                    .get("channel")
//                    .get("atmosphere")
//                    .toString()
//            Gson().fromJson<Atmosphere2>(resultString, Atmosphere2::class.java)
//        } catch (e: Exception) {
//            throw e
//        }
//    }
//}
//
//object ConditionConverter : Converter<ResponseBody, Condition2> {
//    override fun convert(value: ResponseBody): Condition2 {
//        return try {
//            val resultString = JSONObjectAdapter(JSONObject(value.string()))
//                    .get("query")
//                    .get("results")
//                    .get("channel")
//                    .get("item")
//                    .get("condition")
//                    .toString()
//            Gson().fromJson<Condition2>(resultString, Condition2::class.java)
//        } catch (e: Exception) {
//            throw e
//        }
//    }
//
//}
//
//object ForecastConverter : Converter<ResponseBody, Forecast2> {
//    override fun convert(value: ResponseBody): Forecast2 {
//        return try {
//            val resultString = JSONObjectAdapter(JSONObject(value.string()))
//                    .get("query")
//                    .get("results")
//                    .toString()
//            Gson().fromJson<Forecast2>(resultString, Forecast2::class.java)
//        } catch (e: Exception) {
//            throw e
//        }
//    }
//
//}
//
//object WindConverter : Converter<ResponseBody, Wind2> {
//    override fun convert(value: ResponseBody): Wind2 {
//        return try {
//            val resultString = JSONObjectAdapter(JSONObject(value.string()))
//                    .get("query")
//                    .get("results")
//                    .get("channel")
//                    .get("wind")
//                    .toString()
//            Gson().fromJson<Wind2>(resultString, Wind2::class.java)
//        }catch (e: Exception) {
//            throw e
//        }
//    }
//
//}