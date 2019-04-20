package com.tans.tweather2.utils

import com.squareup.moshi.Moshi
import com.tans.tweather2.api.ApiClient

inline fun <reified T> T.toJson(moshi: Moshi = ApiClient.moshi)
        : String = moshi.adapter<T>(T::class.java).toJson(this)


inline fun <reified T> String.fromJson(moshi: Moshi = ApiClient.moshi)
        : T? = moshi.adapter<T>(T::class.java).fromJson(this)
