package com.tans.tweather2.ui.cities

import android.app.Activity
import arrow.core.none
import arrow.core.some
import com.tans.tweather2.core.InputOwner
import com.tans.tweather2.entites.City
import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.ui.BaseViewModel
import com.tans.tweather2.utils.switchThread
import io.reactivex.Completable
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject

class CitiesViewModel @Inject constructor(private val citiesRepository: CitiesRepository)
    : BaseViewModel<CitiesOutputState, CitiesInput>(defaultState = CitiesOutputState()) {

    override fun inputUpdate(input: CitiesInput?, inputOwner: InputOwner) {
        with(inputOwner) {
            input?.nextChildren?.switchMapCompletable { parentCity ->
                citiesRepository.getCities(parentId = parentCity.id, level = parentCity.level + 1)
                        .switchThread()
                        .flatMapCompletable { children ->
                            updateState {
                                val oldChain = it.citiesChainAndChildren
                                val newChain: List<ParentAndChildren> = oldChain.toMutableList().apply { add(parentCity.some() to children) }
                                it.copy(citiesChainAndChildren = newChain)
                            }
                        }
            }?.bindInputLife()

            input?.backPress
                    ?.withLatestFrom(bindOutputState().map { it.citiesChainAndChildren })
                    ?.flatMapCompletable { (_, citiesChain) ->
                        if (citiesChain.size <= 1) {
                            Completable.fromAction {
                                if (this is Activity) {
                                    finish()
                                }
                            }
                        } else {
                            updateState {
                                it.copy(citiesChainAndChildren = citiesChain.take(citiesChain.size - 1))
                            }
                        }
                    }?.bindInputLife()
        }
    }

    override fun initWithCompletable(): Completable = citiesRepository.getRootCites()
            .flatMapCompletable { rootCities ->
                updateState { state ->
                    state.copy(citiesChainAndChildren = listOf(none<City>() to rootCities))
                }
            }

}