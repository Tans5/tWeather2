package com.tans.tweather2.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.tans.tweather2.TWeatherApplication
import com.tans.tweather2.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjection

object Injector {

    fun appInject(app: TWeatherApplication) {
        DaggerApplicationComponent.builder()
                .application(app)
                .build()
                .inject(app)
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                AndroidInjection.inject(activity)
            }

        })
    }
}