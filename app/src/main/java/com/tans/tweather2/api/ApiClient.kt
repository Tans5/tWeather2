package com.tans.tweather2.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tans.tweather2.BuildConfig
import com.tans.tweather2.api.moshiadapter.DateIntAdapter
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

    val moshi: Moshi = Moshi
            .Builder()
             .add(Date::class.java, DateIntAdapter)
            // .add(Types.newParameterizedType(List::class.java, City::class.java), CitiesAdapter)
            .add(KotlinJsonAdapterFactory())
            .build()

    private val baseRetrofitClientBuilder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(createBaseOkHttpClientBuilder().build())

    fun retrofitClientBuilder(clientType: ClientType): Retrofit.Builder {
        return baseRetrofitClientBuilder
                .baseUrl(clientType.baseUrl)
    }

    fun createBaseOkHttpClientBuilder(): OkHttpClient.Builder {
        val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(CityInterceptor)
        return if (BuildConfig.isDebug) {
            clientBuilder.addNetworkInterceptor(StethoInterceptor())
            clientBuilder.sslSocketFactory(createUnsafeSslSocketFactory(), createUnsafeTrustManager())
        } else {
            clientBuilder
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
        object Bing : ClientType(tag = "bing", baseUrl = "https://cn.bing.com/")
    }

}

