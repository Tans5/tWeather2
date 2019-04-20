package com.tans.tweather2.ui.splash

import arrow.core.Option
import arrow.core.none
import com.tans.tweather2.entites.City

data class SplashOutputState(val hasFavorCity: Boolean = false,
                             val choseCity: Option<City> = none())