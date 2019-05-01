package com.tans.tweather2.ui.cities

import com.tans.tweather2.entites.City
import io.reactivex.Observable

data class CitiesInput(
        val nextChildren: Observable<City>,
        val backPress: Observable<Unit>)