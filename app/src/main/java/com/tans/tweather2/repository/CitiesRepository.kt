package com.tans.tweather2.repository

import com.tans.tweather2.api.service.Cities
import com.tans.tweather2.api.service.CitiesService
import com.tans.tweather2.db.CityDao
import com.tans.tweather2.entites.City
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CitiesRepository @Inject constructor(private val citiesService: CitiesService,
                                           private val citiesDao: CityDao) {

    fun getRootCites(): Single<Cities> = getCitiesPrivate(null, 1)

    fun getCities(parentId: Long, level: Int): Single<Cities> = getCitiesPrivate(parentId, level)

    fun updateCity(city: City): Completable = Completable.fromAction {
        citiesDao.insert(city)
    }

    private fun getCitiesPrivate(parentId: Long?, level: Int): Single<Cities> = if (parentId == null) {
        citiesDao.queryRootCites()
    } else {
        citiesDao.queryByParentId(parentId = parentId.toString())
    }.flatMapSingle { cities ->
        if (cities.isEmpty()) {
            citiesService.getCities(parentCode = parentId?.toString())
                    .map { cities ->
                        val fixedCities = cities.map { it.copy(parentId = parentId, level = level) }
                        citiesDao.insert(fixedCities)
                        fixedCities
                    }
        } else {
            Single.just(cities)
        }
    }
}