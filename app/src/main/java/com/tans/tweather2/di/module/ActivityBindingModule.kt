package com.tans.tweather2.di.module

import com.example.tanstan.dagger2demo.di.ActivityScope
import com.tans.tweather2.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainActivityInjector() : MainActivity
}