package com.tans.tweather2.utils

import com.tans.tweather2.api.ApiResponse
import com.tans.tweather2.repository.Either
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<ApiResponse<T>>.enableResponseError(): Single<ApiResponse<T>> =
        this.onErrorReturn {
    ApiResponse.ApiErrorResponse(it)
}

fun <T> Single<T>.switchThread(): Single<T> = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<ApiResponse<T>>.transApiResponse(): Single<Either<Throwable, T>> = this.map {
    when (it) {
        is ApiResponse.ApiErrorResponse -> Either.left(it.t)
        is ApiResponse.ApiEmptyResponse -> Either.left(Throwable("empty"))
        is ApiResponse.ApiSuccessResponse -> Either.right(it.r)
    }
}