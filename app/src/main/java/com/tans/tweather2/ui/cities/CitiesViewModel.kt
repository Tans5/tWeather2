package com.tans.tweather2.ui.cities

import android.app.Activity
import androidx.databinding.ViewDataBinding
import arrow.core.none
import arrow.core.some
import com.tans.tweather2.entites.City
import com.tans.tweather2.repository.CitiesRepository
import com.tans.tweather2.ui.BaseActivity
import com.tans.tweather2.ui.BaseViewModel
import javax.inject.Inject

class CitiesViewModel @Inject constructor(private val citiesRepository: CitiesRepository)
    : BaseViewModel<CitiesOutputState, CitiesInput>(defaultState = CitiesOutputState()) {

    override fun inputUpdate(input: CitiesInput?, activity: BaseActivity<out BaseViewModel<*, *>, out ViewDataBinding, *, *>) {
        with(activity) {
            input?.nextChildren?.filter {
                if (it.level >= 3) {
                    setResult(Activity.RESULT_OK, CitiesActivity.createResultIntent(it))
                    finish()
                    false
                } else {
                    true
                }
            }?.flatMapCompletable { parentCity ->
                citiesRepository.getCities(parentId = parentCity.id, level = parentCity.level + 1)
                        .flatMapCompletable { children ->
                            updateOutputState {
                                val oldChain = it.citiesChain
                                val newChain: List<CitiesAndParent> = oldChain.toMutableList().apply { add(parentCity.some() to children) }
                                it.copy(citiesChain = newChain)
                            }
                        }
            }?.bindInputLifecycle()

        }
    }

    override fun outputStateInitLoad() {
        citiesRepository.getRootCites()
                .flatMapCompletable { rootCities ->
                    updateOutputState { state ->
                        state.copy(citiesChain = listOf(none<City>() to rootCities))
                    }
                }
                .bindViewModelLife()
    }


}