package com.tans.tweather2.ui.splash

import android.content.Intent
import android.view.View
import arrow.core.Some
import com.jakewharton.rxbinding3.view.clicks
import com.tans.tweather2.R
import com.tans.tweather2.databinding.ActivitySplashBinding
import com.tans.tweather2.ui.BaseActivity
import com.tans.tweather2.ui.main.MainActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding, SplashOutputState, SplashInput>(SplashViewModel::class.java) {

    override fun layoutId(): Int = R.layout.activity_splash

    override fun init() {

        viewModel.setInput(input = SplashInput(chooseCity = viewDataBinding.choseCityTv.clicks(),
                enterLocation = viewDataBinding.enterTv.clicks()), subscriber = this)

        subScribeState({ it.choseCity }) { city ->
            if (city is Some) {
                viewDataBinding.choseCityTv.text = city.t.cityName
            } else {
                viewDataBinding.choseCityTv.setText(R.string.splash_activity_use_gps)
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
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
                    })
                }
                .ignoreElement()
                .bindLife()
    }

}