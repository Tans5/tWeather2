package com.tans.tweather2

import okhttp3.*
import okio.BufferedSource
import okio.Okio

class MockResponseInterceptor(private val jsonFilePath: String) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val bodySource = readJsonFileAsResource()
        chain.proceed(chain.request())
        return Response.Builder()
                .body(ResponseBody.create(MediaType.get("application/json"),
                        bodySource?.readString(Charsets.UTF_8) ?: ""))
                .headers(Headers.of("Content-Type", "application/json"))
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .request(chain.request())
                .message("This is mock response")
                .build()
    }

    private fun readJsonFileAsResource(): BufferedSource? {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$jsonFilePath")
        return if (inputStream != null) Okio.buffer(Okio.source(inputStream)) else null
    }

}