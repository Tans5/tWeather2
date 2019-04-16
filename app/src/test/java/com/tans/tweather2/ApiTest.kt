package com.tans.tweather2

import com.tans.tweather2.api.ApiClient
import com.tans.tweather2.api.service.CitiesService
import com.tans.tweather2.api.service.WeatherService
import com.tans.tweather2.api.service.getWeather
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test
import java.net.URLEncoder
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class ApiTest {

    @Test
    fun weatherTest2() {

        val appId = "LrwGbl7e"
        val consumerKey = "dj0yJmk9b1BOT3ZOU212YmZlJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWQz"
        val consumerSecret = "6272310b48a68e7709a190b6e3c8d65cbde21688"
        val url = "https://weather-ydn-yql.media.yahoo.com/forecastrss"

        val timestamp = Date().time / 1000

        val oauthNonce = {
            val nonce = ByteArray(32)
            val rand = Random()
            rand.nextBytes(nonce)
            String(nonce).replace("\\W".toRegex(), "")
        }.invoke()

        val params = listOf("oauth_consumer_key=$consumerKey",
                "oauth_nonce=$oauthNonce",
                "oauth_signature_method=HMAC-SHA1",
                "oauth_timestamp=$timestamp",
                "oauth_version=1.0",
                "location=${URLEncoder.encode("成都", "UTF-8")}",
                "u=c",
                "format=json").sorted()

        val signatureString: String = params.withIndex().fold("GET&${URLEncoder.encode(url, "UTF-8")}&") { last, item ->
            "$last${URLEncoder.encode("${if (item.index > 0) "&" else ""}${item.value}", "UTF-8")}"
        }

        val signature: String = {
            val signingKey = SecretKeySpec(("$consumerSecret&").toByteArray(), "HmacSHA1")
            val mac = Mac.getInstance("HmacSHA1")
            mac.init(signingKey)
            val rawHMAC = mac.doFinal(signatureString.toByteArray())
            val encoder = Base64.getEncoder()
            encoder.encodeToString(rawHMAC)
        }.invoke()

        val authLine =  "OAuth " +
                "oauth_consumer_key=\"" + consumerKey + "\", " +
                "oauth_nonce=\"" + oauthNonce + "\", " +
                "oauth_timestamp=\"" + timestamp + "\", " +
                "oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_signature=\"" + signature + "\", " +
                "oauth_version=\"1.0\""

        val okHttpClient = OkHttpClient.Builder().build()

        val request = Request.Builder()
                .url("$url?location=成都&format=json&u=c")
                .addHeader("Authorization", authLine)
                .addHeader("X-Yahoo-App-Id", appId)
                .addHeader("Content-Type", "application/json")
                .build()

        val response = okHttpClient.newCall(request).execute()
        println(response.body()?.string())

    }

    @Test
    fun weatherTest() {
        val service = ApiClient.retrofitClientBuilder(ApiClient.ClientType.Weather)
                .build()
                .create(WeatherService::class.java)
        val weather = service.getWeather(weatherRequest = WeatherService.Companion.WeatherRequest.CityNameRequest(cityName = "成都"))
                .doOnError {
                    println("error: $it")
                }
                .blockingGet()


        println(weather)
    }

    @Test
    fun citiesTest() {
        val service = ApiClient.retrofitClientBuilder(ApiClient.ClientType.City)
                .build()
                .create(CitiesService::class.java)

        val cities = service.getCities()
                .doOnError { println(it) }
                .blockingGet()

        println(cities)
    }

}