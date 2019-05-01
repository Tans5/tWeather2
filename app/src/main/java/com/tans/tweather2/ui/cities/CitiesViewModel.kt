package com.tans.tweather2.ui.cities

import android.app.Activity
import arrow.core.none
import arrow.core.some
import com.tans.tweather2.entites.City
import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.ui.ViewModelSubscriber
import com.tans.tweather2.utils.switchThread
import io.reactivex.Completable
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject

class CitiesViewModel @Inject constructor(private val citiesRepository: CitiesRepository)
    : BaseViewModel<CitiesOutputState, CitiesInput>(defaultState = CitiesOutputState()) {

    override fun inputUpdate(input: CitiesInput?, subscriber: ViewModelSubscriber) {
        with(subscriber) {
            input?.nextChildren?.filter {
                if (it.level >= 3) {
                    if (this is Activity) {
                        setResult(Activity.RESULT_OK, CitiesActivity.createResultIntent(it))
                        finish()
                    }
                    false
                } else {
                    true
                }
            }?.switchMapCompletable { parentCity ->
                citiesRepository.getCities(parentId = parentCity.id, level = parentCity.level + 1)
                        .switchThread()
                        .flatMapCompletable { children ->
                            updateOutputState {
                                val oldChain = it.citiesChain
                                val newChain: List<CitiesAndParent> = oldChain.toMutableList().apply { add(parentCity.some() to children) }
                                it.copy(citiesChain = newChain)
                            }
                        }
            }?.bindInputLifecycle()

            input?.backPress
                    ?.withLatestFrom(bindOutputState().map { it.citiesChain })
                    ?.flatMapCompletable { (_, citiesChain) ->
                        if (citiesChain.size <= 1) {
                            if (this is Activity) {
                                Completable.fromAction { finish() }
                            } else {
                                Completable.complete()
                            }
                        } else {
                            updateOutputState {
                                it.copy(citiesChain = citiesChain.take(citiesChain.size - 1))
                            }
                        }
                    }?.bindInputLifecycle()

        }
    }

    override fun outputStateInitLoad() {
        citiesRepository.getRootCites()
                .switchThread()
                .flatMapCompletable { rootCities ->
                    updateOutputState { state ->
                        state.copy(citiesChain = listOf(none<City>() to rootCities))
                    }
                }
                .bindLife()
    }


}