package com.tans.tweather2.repository.resource

import com.tans.tweather2.api.service.CitiesService
import com.tans.tweather2.api.service.LocationService
import com.tans.tweather2.api.service.WeatherService
import com.tans.tweather2.entites.Atmosphere
import com.tans.tweather2.entites.Condition
import com.tans.tweather2.entites.Forecast
import com.tans.tweather2.entites.Wind
import io.reactivex.Single
import javax.inject.Inject

class RemoteResource @Inject constructor(
        private val locationService: LocationService,
        private val weatherService: WeatherService,
        private val citiesService: CitiesService
) {

    fun getWeatherAtmosphere(city: String): Single<Atmosphere> = weatherService.getAtmosphere(WeatherService.createYql(
            type = WeatherService.Companion.WeatherQueryType.ATMOSPHERE,
            city = city))

    fun getWeatherForecast(city: String): Single<Forecast> = weatherService.getForecast(WeatherService.createYql(
            type = WeatherService.Companion.WeatherQueryType.FORECAST,
            city = city))

    fun getWeatherWind(city: String): Single<Wind> = weatherService.getWind(WeatherService.createYql(
            type = WeatherService.Companion.WeatherQueryType.WIND,
            city = city))

    fun getWeatherCondition(city: String): Single<Condition> = weatherService.getCondition(WeatherService.createYql(
            type = WeatherService.Companion.WeatherQueryType.CONDITION,
            city = city))

}