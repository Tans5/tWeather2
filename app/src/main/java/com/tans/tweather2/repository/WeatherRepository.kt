package com.tans.tweather2.repository

import com.tans.tweather2.api.service.WeatherService
import javax.inject.Inject

class WeatherRepository @Inject constructor(
        private val remoteResource: WeatherService
) {

}