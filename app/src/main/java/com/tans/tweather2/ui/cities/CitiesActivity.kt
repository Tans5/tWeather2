package com.tans.tweather2.ui.cities

import android.content.Context
import android.content.Intent
import arrow.core.or
import com.tans.tweather2.R
import com.tans.tweather2.databinding.ActivityCitiesBinding
import com.tans.tweather2.entites.City
import com.tans.tweather2.ui.BaseActivity
import com.tans.tweather2.utils.callToObservable
import com.tans.tweather2.utils.fromJson
import com.tans.tweather2.utils.toJson

class CitiesActivity
    : BaseActivity<CitiesViewModel, ActivityCitiesBinding, CitiesOutputState, CitiesInput>(viewModelClazz = CitiesViewModel::class.java) {

    override fun layoutId(): Int = R.layout.activity_cities

    override fun init() {

        val (cityObs, cityCall ) = callToObservable<City>()
        val citiesAdapter = CitiesAdapter(cityCall)
        viewDataBinding.citiesRv.adapter = citiesAdapter

        viewModel.setInput(input = CitiesInput(nextChildren = cityObs),
                activity = this)

        subScribeState({ it.citiesChain }) {
            val item = it.getOrNull(it.lastIndex)
            viewDataBinding.title.title = item?.first?.orNull()?.cityName ?: getString(R.string.splash_activity_choose_city)
            citiesAdapter.submitList(item?.second)
        }

    }


    companion object {
        private const val CITY_RESULT_KEY = "city_result_key"

        fun createResultIntent(city: City): Intent = Intent().apply { putExtra(CITY_RESULT_KEY, city.toJson()) }

        fun getResultData(intent: Intent): City? = intent.getStringExtra(CITY_RESULT_KEY).fromJson()

        fun getIntent(context: Context): Intent = Intent(context, CitiesActivity::class.java)
    }

}