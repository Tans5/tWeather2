package com.tans.tweather2.api.service

import com.tans.tweather2.entites.Weather
import io.reactivex.Single
import retrofit2.http.GET

interface WeatherService {

    @GET("forecastrss/")
    fun getWeather(): Single<Weather>

    companion object {

    }

}