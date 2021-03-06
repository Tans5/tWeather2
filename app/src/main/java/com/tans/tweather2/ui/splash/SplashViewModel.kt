package com.tans.tweather2.ui.splash

import android.app.Activity
import android.content.Intent
import arrow.core.Some
import arrow.core.some
import com.tans.tweather2.core.InputOwner
import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.ui.BaseActivity
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.ui.cities.CitiesActivity
import com.tans.tweather2.ui.main.MainActivity
import com.tans.tweather2.utils.switchThread
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val citiesRepository: CitiesRepository) : BaseViewModel<SplashOutputState, SplashInput>(SplashOutputState()) {
    override fun inputUpdate(input: SplashInput?, inputOwer: InputOwner) {
        with(inputOwer) {

            input?.chooseCity
                    ?.flatMapMaybe {
                        if (this is BaseActivity<*, *, *, *>) {
                            startActivityForResult(CitiesActivity.getIntent(this))
                                    .map { CitiesActivity.getResultData(it) }
                        } else {
                            Maybe.empty()
                        }
                    }
                    ?.flatMapCompletable { city ->
                        updateState { it.copy(choseCity = city.some()) }
                    }?.bindInputLife()

            input?.enterLocation
                    ?.withLatestFrom(bindOutputState().map { it.choseCity })
                    ?.flatMapCompletable { (_, choseCity) ->
                        if (choseCity is Some) {
                            citiesRepository.addCityToFavor(choseCity.t)
                                    .switchThread()
                        } else {
                            Completable.complete()
                        }.andThen {
                            if (this is Activity) {
                                startActivity(Intent(this, MainActivity::class.java).apply {
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                })
                                overridePendingTransition(0, 0)
                            }
                        }
                    }
                    ?.bindInputLife()
        }
    }


    override fun init() {

        citiesRepository.getFavorCitiesSize()
                .switchThread()
                .flatMapCompletable { size ->
                    updateState { state ->
                        val hasFavorCity = size > 0
                        state.copy(hasFavorCity = hasFavorCity)
                    }
                }
                .bindLife()

    }


}