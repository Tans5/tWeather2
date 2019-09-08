package com.tans.tweather2.ui.cities

import arrow.core.Option
import com.tans.tweather2.api.service.Cities
import com.tans.tweather2.entites.City

data class CitiesOutputState(
        val citiesChainAndChildren: List<ParentAndChildren> = emptyList())

typealias ParentAndChildren = Pair<Option<City>, Cities>