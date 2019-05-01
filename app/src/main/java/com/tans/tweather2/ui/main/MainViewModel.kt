package com.tans.tweather2.ui.main

import com.tans.tweather2.entites.City
import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.repository.ImagesRepository
import com.tans.tweather2.repository.WeatherRepository
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.ui.ViewModelSubscriber
import com.tans.tweather2.utils.switchThread
import javax.inject.Inject

class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository,
                                        private val citiesRepository: CitiesRepository,
                                        private val imagesRepository: ImagesRepository)
    : BaseViewModel<MainOutputState, MainInput>(defaultState = MainOutputState()) {
    override fun inputUpdate(input: MainInput?, subscriber: ViewModelSubscriber) {
        with(subscriber) {
            input?.unit?.bindInputLifecycle()
        }
    }


    override fun outputStateInitLoad() {

    }

    fun testCities() {
        citiesRepository.getRootCites()
                .switchThread()
                .doOnSuccess {
                    println(it)
                }
                .bindLife()
    }

    fun testWeather() {
        weatherRepository.getWeather(City(id = -1, cityName = "成都"))
                .switchThread()
                .doOnNext {
                    println(it)
                }
                .bindLife()
    }

    fun testImages() {
        imagesRepository.getImages()
                .switchThread()
                .doOnNext {
                    println(it)
                }
                .bindLife()

    }

}
