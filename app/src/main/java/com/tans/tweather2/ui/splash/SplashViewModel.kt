package com.tans.tweather2.ui.splash

import androidx.databinding.ViewDataBinding
import arrow.core.some
import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.ui.BaseActivity
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.utils.switchThread
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val citiesRepository: CitiesRepository) : BaseViewModel<SplashOutputState, SplashInput>(SplashOutputState()) {
    override fun inputUpdate(input: SplashInput?, activity: BaseActivity<out BaseViewModel<*, *>, out ViewDataBinding, *, *>) {
        with(activity) {
            input?.chooseCity?.flatMapCompletable { city ->
                updateOutputState { it.copy(choseCity = city.some()) }
            }?.bindInputLifecycle()
        }
    }


    override fun outputStateInitLoad() {

        citiesRepository.getFavorCitiesSize()
                .switchThread()
                .flatMapCompletable { size ->
                    updateOutputState {state ->
                        val hasFavorCity = size > 0
                        state.copy(hasFavorCity = hasFavorCity)
                    }
                }
                .bindViewModelLife()

    }


}