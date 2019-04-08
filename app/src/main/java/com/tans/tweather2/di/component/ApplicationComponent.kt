package com.tans.tweather2.di.component
import com.tans.tweather2.TWeatherApplication
import com.tans.tweather2.di.module.ActivityBindingModule
import com.tans.tweather2.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ActivityBindingModule::class, AndroidInjectionModule::class, AndroidSupportInjectionModule::class])
interface ApplicationComponent : AndroidInjector<TWeatherApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: TWeatherApplication) : Builder
        fun build() : ApplicationComponent
    }
}