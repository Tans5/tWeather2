package com.tans.tweather2.ui.main

import com.tans.tweather2.repository.Repository
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.utils.switchThread
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    fun fetchWeatherAtmosphere(city: String) {
        repository.getWeatherAtmosphere(city)
                .switchThread()
                .bindViewModelLife()
    }

    fun fetchWeatherWind(city: String) {
        repository.getWeatherWind(city)
                .switchThread()
                .bindViewModelLife()
    }

    fun fetchWeatherCondition(city: String) {
        repository.getWeatherCondition(city)
                .switchThread()
                .bindViewModelLife()
    }

    fun fetchWeatherForecast(city: String) {
        repository.getWeatherForecast(city)
                .switchThread()
                .bindViewModelLife()
    }
}
