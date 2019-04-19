package com.tans.tweather2

import com.facebook.stetho.Stetho

class DebugApplication : TWeatherApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

}