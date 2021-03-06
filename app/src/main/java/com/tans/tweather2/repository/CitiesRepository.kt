package com.tans.tweather2.repository

import com.tans.tweather2.api.service.Cities
import com.tans.tweather2.api.service.CitiesService
import com.tans.tweather2.db.CityDao
import com.tans.tweather2.entites.City
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class CitiesRepository @Inject constructor(private val citiesService: CitiesService,
                                           private val citiesDao: CityDao) {

    fun getRootCites(): Single<Cities> = getCitiesPrivate(null, 1)

    fun getCities(parentId: Long, level: Int): Single<Cities> = getCitiesPrivate(parentId, level)

    fun addCityToFavor(city: City): Completable = if (city.favorOrder > 0) {
        Completable.complete()
    } else {
        citiesDao.maxFavorOrder()
                .switchIfEmpty(Single.just(0))
                .flatMapCompletable {
                    citiesDao.insert(city.copy(favorOrder = it + 1))
                }
    }

    fun updateCity(city: City): Completable = citiesDao.insert(city)

    fun getFavorCitiesSize(): Maybe<Int> = citiesDao.favorCitySize()

    private fun getCitiesPrivate(parentId: Long?, level: Int): Single<Cities> = if (parentId == null) {
        citiesDao.queryRootCites()
    } else {
        citiesDao.queryByParentId(parentId = parentId.toString())
    }.flatMapSingle { cities ->
        if (cities.isEmpty()) {
            citiesService.getCities(parentCode = parentId?.let {
                val idString = it.toString()
                if (idString.length % 2 == 0) {
                    idString
                } else {
                    "0$idString"
                }
            } ?: "")
                    .map { cities1 ->
                        val fixedCities = cities1.map { it.copy(parentId = parentId ?: -1, level = level) }
                        fixedCities
                    }
                    .flatMap { fixedCities ->
                        citiesDao.insert(fixedCities)
                                .andThen(Single.just(fixedCities))
                    }
        } else {
            Single.just(cities)
        }
    }
}