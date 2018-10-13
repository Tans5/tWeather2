package com.tans.tweather2.repository

import com.tans.tweather2.api.ApiResponse
import com.tans.tweather2.entites.Atmosphere
import com.tans.tweather2.entites.Condition
import com.tans.tweather2.entites.Forecast
import com.tans.tweather2.entites.Wind
import com.tans.tweather2.repository.resource.LocalResource
import com.tans.tweather2.repository.resource.RemoteResource
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
        private val localResource: LocalResource,
        private val remoteResource: RemoteResource
) {
    fun getWeatherAtmosphere(city: String): Single<ApiResponse<Atmosphere>> =
            remoteResource.getWeatherAtmosphere(city)

    fun getWeatherCondition(city: String): Single<ApiResponse<Condition>> =
            remoteResource.getWeatherCondition(city)

    fun getWeatherForecast(city: String): Single<ApiResponse<Forecast>> =
            remoteResource.getWeatherForecast(city)

    fun getWeatherWind(city: String): Single<ApiResponse<Wind>> =
            remoteResource.getWeatherWind(city)
}