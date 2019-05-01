package com.tans.tweather2

import com.facebook.stetho.Stetho
import timber.log.Timber

class DebugApplication : TWeatherApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())
    }

}