package com.tans.tweather2.repository

import com.tans.tweather2.entites.Atmosphere2
import com.tans.tweather2.entites.Condition2
import com.tans.tweather2.entites.Forecast2
import com.tans.tweather2.entites.Wind2
import com.tans.tweather2.repository.resource.LocalResource
import com.tans.tweather2.repository.resource.RemoteResource
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
        private val localResource: LocalResource,
        private val remoteResource: RemoteResource
) {
    fun getWeatherAtmosphere(city: String): Single<Atmosphere2> = remoteResource.getWeatherAtmosphere(city)

    fun getWeatherCondition(city: String): Single<Condition2> = remoteResource.getWeatherCondition(city)

    fun getWeatherForecast(city: String): Single<Forecast2> = remoteResource.getWeatherForecast(city)

    fun getWeatherWind(city: String): Single<Wind2> = remoteResource.getWeatherWind(city)
}