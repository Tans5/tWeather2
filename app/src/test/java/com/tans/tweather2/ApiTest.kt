package com.tans.tweather2

import com.tans.tweather2.api.ApiClient
import com.tans.tweather2.api.service.WeatherService
import org.junit.Test

class ApiTest {

    @Test
    fun weatherAtmosphereTest() {

        val atmosphere = ApiClient.retrofitClientBuilder(ApiClient.ClientType.Weather)
                .build()
                .create(WeatherService::class.java)
                .getAtmosphere(WeatherService.createYql(WeatherService.Companion.WeatherQueryType.ATMOSPHERE, "成都"))
                .blockingGet()
        println(atmosphere)
    }

}