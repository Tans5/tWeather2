package com.tans.tweather2.repository

import com.tans.tweather2.api.ApiResponse
import com.tans.tweather2.entites.Atmosphere
import com.tans.tweather2.entites.Condition
import com.tans.tweather2.entites.Forecast
import com.tans.tweather2.entites.Wind
import com.tans.tweather2.repository.resource.LocalResource
import com.tans.tweather2.repository.resource.RemoteResource
import com.tans.tweather2.utils.transApiResponse
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
        private val localResource: LocalResource,
        private val remoteResource: RemoteResource
) {
    fun getWeatherAtmosphere(city: String): Single<Either<Throwable, Atmosphere>> =
            remoteResource.getWeatherAtmosphere(city)
                    .transApiResponse()

    fun getWeatherCondition(city: String): Single<Either<Throwable, Condition>> =
            remoteResource.getWeatherCondition(city)
                    .transApiResponse()

    fun getWeatherForecast(city: String): Single<Either<Throwable, Forecast>> =
            remoteResource.getWeatherForecast(city)
                    .transApiResponse()

    fun getWeatherWind(city: String): Single<Either<Throwable, Wind>> =
            remoteResource.getWeatherWind(city)
                    .transApiResponse()
}