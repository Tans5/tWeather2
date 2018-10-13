package com.tans.tweather2.repository

import com.tans.tweather2.entites.Atmosphere
import com.tans.tweather2.repository.resource.LocalResource
import com.tans.tweather2.repository.resource.RemoteResource
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
        private val localResource: LocalResource,
        private val remoteResource: RemoteResource
) {
    fun getWeatherAtmosphere(city: String): Single<Atmosphere> = remoteResource.getWeatherAtmosphere(city)
}