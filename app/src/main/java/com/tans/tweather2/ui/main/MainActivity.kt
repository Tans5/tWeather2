package com.tans.tweather2.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tans.tweather2.R
import com.tans.tweather2.viewmodel.TWeatherViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: TWeatherViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(this::class.java.simpleName, "$viewModel")
        viewModel.fetchWeatherAtmosphere("成都")
    }
}
