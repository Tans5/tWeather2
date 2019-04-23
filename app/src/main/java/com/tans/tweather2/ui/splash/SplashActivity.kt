package com.tans.tweather2.ui.splash

import android.content.Intent
import android.view.View
import arrow.core.Some
import com.google.android.material.bottomappbar.BottomAppBar
import com.jakewharton.rxbinding3.view.clicks
import com.tans.tweather2.R
import com.tans.tweather2.databinding.ActivitySplashBinding
import com.tans.tweather2.entites.City
import com.tans.tweather2.ui.BaseActivity
import com.tans.tweather2.ui.main.MainActivity
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding, SplashOutputState, SplashInput>(SplashViewModel::class.java) {

    override fun layoutId(): Int = R.layout.activity_splash

    override fun init() {

        viewModel.setInput(input = SplashInput(chooseCity = viewDataBinding.cityTv.clicks(),
                currentLocation = viewDataBinding.currentLocationTv.clicks()), activity = this)

        viewDataBinding.currentLocationTv.clicks()
                .bindActivityLife()

        subScribeState({ it.choseCity }) { city ->
            if (city is Some) {
                viewDataBinding.cityTv.text = city.t.cityName
            } else {
                viewDataBinding.cityTv.setText(R.string.splash_activity_choose_city)
            }

        }

        subScribeState({ it.hasFavorCity }) {
            if (it) {
                viewDataBinding.noFavorCityLayout.visibility = View.INVISIBLE
                sleepAndMainActivity(500)
            } else {
                viewDataBinding.noFavorCityLayout.visibility = View.VISIBLE
            }
        }

    }

    private fun sleepAndMainActivity(sleep: Long) {
        Single.just(Unit)
                .delay(sleep, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    overridePendingTransition(0, 0)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .ignoreElement()
                .bindActivityLife()
    }

}