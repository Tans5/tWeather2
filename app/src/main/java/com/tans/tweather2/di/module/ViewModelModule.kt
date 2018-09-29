package com.tans.tweather2.di.module

import android.arch.lifecycle.ViewModelProvider
import com.example.tanstan.dagger2demo.di.ViewModel
import com.tans.tweather2.ui.main.MainViewModel
import com.tans.tweather2.viewmodel.TWeatherViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModel(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel) : android.arch.lifecycle.ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: TWeatherViewModelFactory) : ViewModelProvider.Factory
}