package com.tans.tweather2.ui.main

import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.repository.WeatherRepository
import com.tans.tweather2.ui.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository,
                                        private val citiesRepository: CitiesRepository) : BaseViewModel() {

}
