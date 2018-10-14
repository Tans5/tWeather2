package com.tans.tweather2.api

import com.tans.tweather2.BuildConfig
import com.tans.tweather2.api.converter.WeatherConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

object ApiClient {
    private const val LOCATION_BASE_URL = "http://com.tans.tweather2.api.map.baidu.com/"

    private const val CITIES_BASE_URL = "http://www.weather.com.cn/"

    private const val WEATHER_BASE_URL = "https://query.yahooapis.com/"

    fun locationApiClientBuilder() = baseRetrofitClientBuilder()
            .baseUrl(LOCATION_BASE_URL)!!

    fun weatherApiClientBuilder() = baseRetrofitClientBuilder()
            .baseUrl(WEATHER_BASE_URL)!!
            .addConverterFactory(WeatherConverterFactory)

    fun citesApiClientBuilder() = baseRetrofitClientBuilder()
            .baseUrl(CITIES_BASE_URL)!!

    private fun baseRetrofitClientBuilder() : Retrofit.Builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient())

    private fun createOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return if (BuildConfig.isDebug) {
            clientBuilder.sslSocketFactory(createUnsafeSslSocketFactory(), createUnsafeTrustManager())
                    .build()
        } else {
            clientBuilder.build()
        }
    }

    private fun createUnsafeTrustManager(): X509TrustManager = object : X509TrustManager {
        override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
        }

        override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()

    }

    private fun createUnsafeSslSocketFactory(): SSLSocketFactory = SSLContext.getInstance("TLS").let {
        it.init(null, arrayOf(createUnsafeTrustManager()), SecureRandom())
        it.socketFactory
    }
}

