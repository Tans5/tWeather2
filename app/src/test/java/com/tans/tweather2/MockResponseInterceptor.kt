package com.tans.tweather2

import okhttp3.*
import okio.BufferedSource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.Headers.Companion.headersOf
import okio.buffer
import okio.source

class MockResponseInterceptor(private val jsonFilePath: String) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val bodySource = readJsonFileAsResource()
        chain.proceed(chain.request())
        return Response.Builder()
                .body((bodySource?.readString(Charsets.UTF_8) ?: "").toResponseBody("application/json".toMediaType()))
                .headers(headersOf("Content-Type", "application/json"))
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .request(chain.request())
                .message("This is mock response")
                .build()
    }

    private fun readJsonFileAsResource(): BufferedSource? {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$jsonFilePath")
        return inputStream?.source()?.buffer()
    }

}