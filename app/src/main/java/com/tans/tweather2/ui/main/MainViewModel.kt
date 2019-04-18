package com.tans.tweather2.ui.main

import com.tans.tweather2.entites.City
import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.repository.ImagesRepository
import com.tans.tweather2.repository.WeatherRepository
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.utils.switchThread
import javax.inject.Inject

class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository,
                                        private val citiesRepository: CitiesRepository,
                                        private val imagesRepository: ImagesRepository) : BaseViewModel() {

    fun testCities() {
        citiesRepository.getRootCites()
                .switchThread()
                .doOnSuccess {
                    println(it)
                }
                .bindViewModelLife()
    }

    fun testWeather() {
        weatherRepository.getWeather(City(id = -1, cityName = "成都"))
                .switchThread()
                .doOnNext {
                    println(it)
                }
                .binViewModelLife()
    }

    fun testImages() {
        imagesRepository.getImages()
                .switchThread()
                .doOnNext {
                    println(it)
                }
                .binViewModelLife()

    }

}
