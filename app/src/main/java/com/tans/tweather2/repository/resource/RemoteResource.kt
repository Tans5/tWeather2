package com.tans.tweather2.repository.resource

import com.tans.tweather2.api.service.CitiesService
import com.tans.tweather2.api.service.LocationService
import com.tans.tweather2.api.service.WeatherService
import com.tans.tweather2.entites.Atmosphere
import io.reactivex.Single
import javax.inject.Inject

class RemoteResource @Inject constructor(
        private val locationService: LocationService,
        private val weatherService: WeatherService,
        private val citiesService: CitiesService
) {

    fun getWeatherAtmosphere(city: String): Single<Atmosphere>
            = weatherService.getAtmosphere(WeatherService.createYql(
            type = WeatherService.Companion.WeatherQueryType.ATMOSPHERE,
            city = city
            ))
}