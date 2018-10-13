package com.tans.tweather2.ui.main

import android.arch.lifecycle.ViewModel
import com.tans.tweather2.api.ApiResponse
import com.tans.tweather2.repository.Repository
import com.tans.tweather2.utils.switchThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun fetchWeatherAtmosphere(city: String) {
        repository.getWeatherAtmosphere(city)
                .switchThread()
                .subscribe{ it ->
                    when(it) {
                        is ApiResponse.ApiSuccessResponse -> println("Success: ${it.r}.")
                        is ApiResponse.ApiEmptyResponse -> println("Response empty!!")
                        is ApiResponse.ApiErrorResponse -> println("Error: ${it.t}")
                    }
                }
    }

    fun fetchWeatherWind(city: String) {
        repository.getWeatherWind(city)
                .switchThread()
                .subscribe{ it ->
                    when(it) {
                        is ApiResponse.ApiSuccessResponse -> println("Success: ${it.r}.")
                        is ApiResponse.ApiEmptyResponse -> println("Response empty!!")
                        is ApiResponse.ApiErrorResponse -> println("Error: ${it.t}")
                    }
                }
    }

    fun fetchWeatherCondition(city: String) {
        repository.getWeatherCondition(city)
                .switchThread()
                .subscribe{ it ->
                    when(it) {
                        is ApiResponse.ApiSuccessResponse -> println("Success: ${it.r}.")
                        is ApiResponse.ApiEmptyResponse -> println("Response empty!!")
                        is ApiResponse.ApiErrorResponse -> println("Error: ${it.t}")
                    }
                }
    }

    fun fetchWeatherForecast(city: String) {
        repository.getWeatherForecast(city)
                .switchThread()
                .subscribe{ it ->
                    when(it) {
                        is ApiResponse.ApiSuccessResponse -> println("Success: ${it.r}.")
                        is ApiResponse.ApiEmptyResponse -> println("Response empty!!")
                        is ApiResponse.ApiErrorResponse -> println("Error: ${it.t}")
                    }
                }
    }
}
