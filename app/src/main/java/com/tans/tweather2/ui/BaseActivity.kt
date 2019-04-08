package com.tans.tweather2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.tans.tweather2.viewmodel.TWeatherViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel>(viewModelClazz: Class<VM>) : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: TWeatherViewModelFactory

    val viewModel: VM by lazy { ViewModelProviders.of(this, viewModelFactory).get(viewModelClazz) }

}