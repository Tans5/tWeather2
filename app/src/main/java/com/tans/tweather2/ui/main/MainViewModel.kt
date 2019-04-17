package com.tans.tweather2.ui.main

import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.repository.WeatherRepository
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.utils.switchThread
import javax.inject.Inject

class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository,
                                        private val citiesRepository: CitiesRepository) : BaseViewModel() {

    fun testCities() {
        citiesRepository.getRootCites()
                .switchThread()
                .doOnSuccess {
                    println(it)
                }
                .bindViewModelLife()
    }

}
