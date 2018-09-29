package com.tans.tweather2.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object ApiClient {
    private const val LOCATION_BASE_URL = "http://com.tans.tweather2.api.map.baidu.com/"

    private const val CITIES_BASE_URL = "http://www.weather.com.cn/"

    private const val WEATHER_BASE_URL = "https://query.yahooapis.com/"

    fun locationApiClientBuilder() = baseRetrofitClientBuilder()
            .baseUrl(LOCATION_BASE_URL)!!

    fun weatherApiClientBuilder() = baseRetrofitClientBuilder()
            .baseUrl(WEATHER_BASE_URL)!!

    fun citesApiClientBuilder() = baseRetrofitClientBuilder()
            .baseUrl(CITIES_BASE_URL)!!

    private fun baseRetrofitClientBuilder() : Retrofit.Builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())


}

