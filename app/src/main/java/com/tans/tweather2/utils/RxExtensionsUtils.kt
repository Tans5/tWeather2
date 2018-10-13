package com.tans.tweather2.utils

import com.tans.tweather2.api.ApiResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<ApiResponse<T>>.enableResponseError(): Single<ApiResponse<T>> =
        this.onErrorReturn {
    ApiResponse.ApiErrorResponse(it)
}

fun <T> Single<T>.switchThread(): Single<T> = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())