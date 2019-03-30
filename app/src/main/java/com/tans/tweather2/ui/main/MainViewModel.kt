package com.tans.tweather2.ui.main

import androidx.lifecycle.ViewModel
import com.tans.tweather2.api.ApiResponse
import com.tans.tweather2.repository.Either
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
                        is Either.Right -> println("success: ${it.data}")
                        is Either.Left -> println("error: ${it.data}")
                    }
                }
    }

    fun fetchWeatherWind(city: String) {
        repository.getWeatherWind(city)
                .switchThread()
                .subscribe{ it ->
                    when(it) {
                        is Either.Right -> println("success: ${it.data}")
                        is Either.Left -> println("error: ${it.data}")
                    }
                }
    }

    fun fetchWeatherCondition(city: String) {
        repository.getWeatherCondition(city)
                .switchThread()
                .subscribe{ it ->
                    when(it) {
                        is Either.Right -> println("success: ${it.data}")
                        is Either.Left -> println("error: ${it.data}")
                    }
                }
    }

    fun fetchWeatherForecast(city: String) {
        repository.getWeatherForecast(city)
                .switchThread()
                .subscribe{ it ->
                    when(it) {
                        is Either.Right -> println("success: ${it.data}")
                        is Either.Left -> println("error: ${it.data}")
                    }
                }
    }
}
