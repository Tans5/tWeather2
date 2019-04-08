package com.tans.tweather2.api.service

import com.tans.tweather2.entites.Atmosphere
import com.tans.tweather2.entites.Condition
import com.tans.tweather2.entites.Forecast
import com.tans.tweather2.entites.Wind
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("v1/public/yql/")
    fun getAtmosphere(@Query("q") yql: String,
                      @Query("format") format: String = "json",
                      @Query("env") env: String = "store://datables.org/alltableswithkeys"): Single<Atmosphere>

    @GET("v1/public/yql/")
    fun getCondition(@Query("q") yql: String,
                     @Query("format") format: String = "json",
                     @Query("env") env: String = "store://datables.org/alltableswithkeys"): Single<Condition>

    @GET("v1/public/yql/")
    fun getForecast(@Query("q") yql: String,
                    @Query("format") format: String = "json",
                    @Query("env") env: String = "store://datables.org/alltableswithkeys"): Single<Forecast>

    @GET("v1/public/yql/")
    fun getWind(@Query("q") yql: String,
                @Query("format") format: String = "json",
                @Query("env") env: String = "store://datables.org/alltableswithkeys"): Single<Wind>

    companion object {

        enum class WeatherQueryType(val value: String) {
            WIND("wind"),
            CONDITION("item.condition"),
            ATMOSPHERE("atmosphere"),
            FORECAST("item.forecast")
        }

        fun createYql(type: WeatherQueryType, city: String): String
                = """select ${type.value} from weather.forecast
                    where ${if (type == WeatherQueryType.CONDITION || type == WeatherQueryType.FORECAST) "u=\"c\" and" else "" }
                    woeid in (select woeid from geo.places(1) where text="$city")"""
    }
}