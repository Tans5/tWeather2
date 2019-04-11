package com.tans.tweather2.api.service

import android.util.Base64
import com.tans.tweather2.entites.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.net.URLEncoder
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

interface WeatherService {

    @GET("forecastrss")
    fun getWeather(@Header("Authorization") authorization: String,
                   @Header("X-Yahoo-App-Id") appId: String = WeatherService.appId,
                   @Header("Content-Type") contentType: String = "application/json",
                   @Query("location") location: String,
                   @Query("u") u: String,
                   @Query("format") format: String): Single<Weather>

    @GET("forecastrss")
    fun getWeather(@Header("Authorization") authorization: String,
                   @Header("X-Yahoo-App-Id") appId: String = WeatherService.appId,
                   @Header("Content-Type") contentType: String = "application/json",
                   @Query("lat") lat: Double,
                   @Query("lon") long: Double,
                   @Query("u") u: String,
                   @Query("format") format: String): Single<Weather>

    @GET("forecastrss")
    fun getWeather(@Header("Authorization") authorization: String,
                   @Header("X-Yahoo-App-Id") appId: String = WeatherService.appId,
                   @Header("Content-Type") contentType: String = "application/json",
                   @Query("woeid") woeid: Long,
                   @Query("u") u: String,
                   @Query("format") format: String): Single<Weather>

    companion object {
        private const val appId = "LrwGbl7e"
        private const val consumerKey = "dj0yJmk9b1BOT3ZOU212YmZlJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWQz"
        private const val consumerSecret = "6272310b48a68e7709a190b6e3c8d65cbde21688"
        private const val url = "https://weather-ydn-yql.media.yahoo.com/forecastrss"

        sealed class WeatherRequest {

            abstract val u: String
            abstract val format: String

            data class CoordinateRequest(val lat: Double,
                                         val long: Double,
                                         override val u: String = "c",
                                         override val format: String = "json") : WeatherRequest()

            data class CityNameRequest(val cityName: String,
                                       override val u: String = "c",
                                       override val format: String = "json") : WeatherRequest()

            data class WoeidReqeust(val woeid: Long,
                                    override val u: String = "c",
                                    override val format: String = "json") : WeatherRequest()
        }

        fun generateWeatherRequestAuth(weatherRequest: WeatherRequest): String {
            val timestamp = Date().time / 1000

            val oauthNonce = ByteArray(32).let {
                val rand = Random()
                rand.nextBytes(it)
                String(it).replace("\\W".toRegex(), "")
            }

            val params: List<String> = mutableListOf("oauth_consumer_key=$consumerKey",
                    "oauth_nonce=$oauthNonce",
                    "oauth_signature_method=HMAC-SHA1",
                    "oauth_timestamp=$timestamp",
                    "oauth_version=1.0").let {
                it.add("u=${URLEncoder.encode(weatherRequest.u, "UTF-8")}")
                it.add("format=${URLEncoder.encode(weatherRequest.format, "UTF-8")}")
                when (weatherRequest) {

                    is WeatherRequest.CoordinateRequest -> {
                        it.add("lat=${weatherRequest.lat}")
                        it.add("lon=${weatherRequest.long}")
                    }

                    is WeatherRequest.CityNameRequest -> {
                        it.add("location=${URLEncoder.encode(weatherRequest.cityName, "UTF-8")}")
                    }

                    is WeatherRequest.WoeidReqeust -> {
                        it.add("woeid=${weatherRequest.woeid}")
                    }
                }
                it.sorted()
            }

            val signatureString: String = params.withIndex().fold("GET&${URLEncoder.encode(url, "UTF-8")}&") { last, item ->
                "$last${URLEncoder.encode("${if (item.index > 0) "&" else ""}${item.value}", "UTF-8")}"
            }

            val signature: String = SecretKeySpec(("$consumerSecret&").toByteArray(), "HmacSHA1").let {
                val mac = Mac.getInstance("HmacSHA1")
                mac.init(it)
                val rawHMAC = mac.doFinal(signatureString.toByteArray())
                // Base64.encodeToString(rawHMAC, Base64.DEFAULT)
                val encoder = java.util.Base64.getEncoder()
                encoder.encodeToString(rawHMAC)
            }

            return "OAuth " +
                    "oauth_consumer_key=\"" + consumerKey + "\", " +
                    "oauth_nonce=\"" + oauthNonce + "\", " +
                    "oauth_timestamp=\"" + timestamp + "\", " +
                    "oauth_signature_method=\"HMAC-SHA1\", " +
                    "oauth_signature=\"" + signature + "\", " +
                    "oauth_version=\"1.0\""
        }

    }

}

fun WeatherService.getWeather(weatherRequest: WeatherService.Companion.WeatherRequest): Single<Weather> {
    val authorization = WeatherService.generateWeatherRequestAuth(weatherRequest)
    return when (weatherRequest) {
        is WeatherService.Companion.WeatherRequest.CoordinateRequest -> {
            getWeather(authorization = authorization,
                    lat = weatherRequest.lat,
                    long = weatherRequest.long,
                    u = weatherRequest.u,
                    format = weatherRequest.format)
        }

        is WeatherService.Companion.WeatherRequest.WoeidReqeust -> {
            getWeather(authorization = authorization,
                    woeid = weatherRequest.woeid,
                    u = weatherRequest.u,
                    format = weatherRequest.format)
        }

        is WeatherService.Companion.WeatherRequest.CityNameRequest -> {
            getWeather(authorization= authorization,
                    location = weatherRequest.cityName,
                    u = weatherRequest.u,
                    format = weatherRequest.format)
        }
    }
}