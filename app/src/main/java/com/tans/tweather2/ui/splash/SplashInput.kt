package com.tans.tweather2.ui.splash

import com.tans.tweather2.entites.City
import io.reactivex.Observable

data class SplashInput(val chooseCity: Observable<City>)