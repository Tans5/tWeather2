package com.tans.tweather2.api

sealed class ApiResponse<T> {
    class ApiErrorResponse<T>(val t: Throwable) : ApiResponse<T>()

    class ApiSuccessResponse<T>(val r: T) : ApiResponse<T> ()

    class ApiEmptyResponse<T> : ApiResponse<T> ()
}