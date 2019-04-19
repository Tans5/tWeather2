package com.tans.tweather2

import com.tans.tweather2.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class TWeatherApplication : DaggerApplication() {

    override fun applicationInjector()
            : AndroidInjector<out DaggerApplication> = DaggerApplicationComponent.builder()
            .application(this)
            .build()
}