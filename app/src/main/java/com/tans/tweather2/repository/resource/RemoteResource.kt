package com.tans.tweather2.repository.resource

import com.tans.tweather2.api.service.CitiesService
import com.tans.tweather2.api.service.LocationService
import com.tans.tweather2.api.service.WeatherService2
import com.tans.tweather2.entites.Atmosphere2
import com.tans.tweather2.entites.Condition2
import com.tans.tweather2.entites.Forecast2
import com.tans.tweather2.entites.Wind2
import io.reactivex.Single
import javax.inject.Inject

class RemoteResource @Inject constructor(
        private val locationService: LocationService,
        private val weatherService: WeatherService2,
        private val citiesService: CitiesService
) {

    fun getWeatherAtmosphere(city: String): Single<Atmosphere2> = weatherService.getAtmosphere(WeatherService2.createYql(
            type = WeatherService2.Companion.WeatherQueryType.ATMOSPHERE,
            city = city))

    fun getWeatherForecast(city: String): Single<Forecast2> = weatherService.getForecast(WeatherService2.createYql(
            type = WeatherService2.Companion.WeatherQueryType.FORECAST,
            city = city))

    fun getWeatherWind(city: String): Single<Wind2> = weatherService.getWind(WeatherService2.createYql(
            type = WeatherService2.Companion.WeatherQueryType.WIND,
            city = city))

    fun getWeatherCondition(city: String): Single<Condition2> = weatherService.getCondition(WeatherService2.createYql(
            type = WeatherService2.Companion.WeatherQueryType.CONDITION,
            city = city))

}