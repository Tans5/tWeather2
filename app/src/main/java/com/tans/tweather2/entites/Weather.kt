package com.tans.tweather2.entites

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(val location: Location,
                   @Json(name = "current_observation") val currentObservation: CurrentObservation,
                   val forecasts: List<Forecast>)

@JsonClass(generateAdapter = true)
data class Location(val woeid: Long,
                    val city: String,
                    val region: String,
                    val country: String,
                    val lat: Double,
                    val long: Double,
                    @Json(name = "timezone_id") val timezoneId: String)

@JsonClass(generateAdapter = true)
data class CurrentObservation(val wind: Wind,
                              val atmosphere: Atmosphere,
                              val astronomy: Astronomy,
                              val condition: Condition,
                              val pubDate: Long)

@JsonClass(generateAdapter = true)
data class Wind(val chill: Int,
                val direction: Int,
                val speed: Float)

@JsonClass(generateAdapter = true)
data class Atmosphere(val humidity: Int,
                      val visibility: Float,
                      val pressure: Float,
                      val rising: Int)

@JsonClass(generateAdapter = true)
data class Astronomy(val sunrise: String,
                     val sunset: String)

@JsonClass(generateAdapter = true)
data class Condition(val text: String,
                     val code: Int,
                     val temperature: Int)

@JsonClass(generateAdapter = true)
data class Forecast(val day: String,
                    val date: Long,
                    val low: Int,
                    val high: Int,
                    val text: String,
                    val code: Int)