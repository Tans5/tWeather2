package com.tans.tweather2.ui.main

import com.tans.tweather2.api.service.WeatherService
import com.tans.tweather2.api.service.getWeather
import com.tans.tweather2.repository.WeatherRepository
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.utils.switchThread
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: WeatherRepository,
                                        private val weatherService: WeatherService) : BaseViewModel() {

    fun updateWeather() {

        weatherService.getWeather(WeatherService.Companion.WeatherRequest.CityNameRequest(cityName = "chengdu"))
                .switchThread()
                .doOnSuccess {
                    println(it)
                }
                .doOnError {
                    println(it)
                }
                .bindViewModelLife()
    }

}
