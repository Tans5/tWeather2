package com.tans.tweather2.ui.main

import android.os.Bundle
import com.tans.tweather2.R
import com.tans.tweather2.ui.BaseActivity

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.fetchWeatherAtmosphere("成都")
        viewModel.fetchWeatherCondition("成都")
        viewModel.fetchWeatherForecast("成都")
        viewModel.fetchWeatherWind("成都")
    }
}
