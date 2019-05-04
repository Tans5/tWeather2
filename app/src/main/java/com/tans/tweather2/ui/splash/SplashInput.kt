package com.tans.tweather2.ui.splash
import io.reactivex.Observable

data class SplashInput(val chooseCity: Observable<Unit>,
                       val enterLocation: Observable<Unit>)