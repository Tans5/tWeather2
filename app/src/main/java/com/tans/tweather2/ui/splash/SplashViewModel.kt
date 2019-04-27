package com.tans.tweather2.ui.splash

import android.content.Intent
import androidx.databinding.ViewDataBinding
import arrow.core.some
import com.tans.tweather2.entites.City
import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.ui.BaseActivity
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.ui.main.MainActivity
import com.tans.tweather2.utils.switchThread
import io.reactivex.Maybe
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val citiesRepository: CitiesRepository) : BaseViewModel<SplashOutputState, SplashInput>(SplashOutputState()) {
    override fun inputUpdate(input: SplashInput?, activity: BaseActivity<out BaseViewModel<*, *>, out ViewDataBinding, *, *>) {
        with(activity) {

            input?.chooseCity
                    ?.flatMapMaybe { Maybe.empty<City>() }
                    ?.flatMapCompletable { city ->
                        updateOutputState { it.copy(choseCity = city.some()) }
                    }?.bindInputLifecycle()

            input?.currentLocation?.doOnNext {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
                overridePendingTransition(0, 0)
                // finish()
            }?.bindInputLifecycle()
        }
    }


    override fun outputStateInitLoad() {

        citiesRepository.getFavorCitiesSize()
                .switchThread()
                .flatMapCompletable { size ->
                    updateOutputState { state ->
                        val hasFavorCity = size > 0
                        state.copy(hasFavorCity = hasFavorCity)
                    }
                }
                .bindViewModelLife()

    }


}