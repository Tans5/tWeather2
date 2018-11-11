package com.tans.tweather2.api

import com.tans.tweather2.BuildConfig
import com.tans.tweather2.api.converter.CityConverterFactory
import com.tans.tweather2.api.converter.WeatherConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

object ApiClient {

    private val baseRetrofitClientBuilder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient())

    fun retrofitClientBuilder(clientType: ClientType): Retrofit.Builder {
        val converterFactory: Converter.Factory? = when (clientType) {
            is ClientType.Weather -> WeatherConverterFactory
            is ClientType.City -> CityConverterFactory
            is ClientType.Location -> null
        }
        return baseRetrofitClientBuilder
                .baseUrl(clientType.baseUrl)
                .addConverterFactory(converterFactory)
    }

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

    sealed class ClientType(val tag: String, val baseUrl: String) {
        object Location : ClientType(tag = "location", baseUrl = "http://com.tans.tweather2.api.map.baidu.com/")
        object City : ClientType(tag = "city", baseUrl = "http://www.weather.com.cn/")
        object Weather : ClientType(tag = "weather", baseUrl = "https://query.yahooapis.com/")
    }

}

