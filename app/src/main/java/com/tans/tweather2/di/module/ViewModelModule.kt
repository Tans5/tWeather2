package com.tans.tweather2.di.module

import androidx.lifecycle.ViewModelProvider
import com.tans.tweather2.di.ViewModel
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
    abstract fun bindMainViewModel(mainViewModel: MainViewModel) : androidx.lifecycle.ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: TWeatherViewModelFactory) : ViewModelProvider.Factory
}