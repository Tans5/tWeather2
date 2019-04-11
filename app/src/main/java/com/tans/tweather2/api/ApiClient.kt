package com.tans.tweather2.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tans.tweather2.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

object ApiClient {

    private val moshi: Moshi = Moshi
            .Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

    private val baseRetrofitClientBuilder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(createOkHttpClient())

    fun retrofitClientBuilder(clientType: ClientType): Retrofit.Builder {
        return baseRetrofitClientBuilder
                .baseUrl(clientType.baseUrl)
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
        object City : ClientType(tag = "city", baseUrl = "http://www.weather.com.cn/")
        object Weather : ClientType(tag = "weather", baseUrl = "https://weather-ydn-yql.media.yahoo.com/")
    }

}

