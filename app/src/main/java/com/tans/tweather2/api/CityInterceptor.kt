package com.tans.tweather2.api

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.MediaType.Companion.toMediaType

object CityInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        // request.url.
        return if (request.url.toString().contains(ApiClient.ClientType.City.baseUrl) && response.isSuccessful) {
            val citiesString = response.body?.string()
            val resultString = citiesString?.split(",")?.joinToString(separator = ", ", prefix = "[", postfix = "]") { cityString ->
                val city = cityString.split("|")
                "{ \"id\": ${city.getOrNull(0)?.toLongOrNull()}, \"city_name\": \"${city.getOrNull(1)}\"}"
            } ?: "[]"
            response.newBuilder()
                    .body(resultString.toResponseBody("application/json".toMediaType()))
                    .build()
        } else {
            response
        }
    }

}