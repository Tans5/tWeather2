package com.tans.tweather2.repository

import com.tans.tweather2.api.service.WeatherService
import com.tans.tweather2.api.service.getWeather
import com.tans.tweather2.db.WeatherDao
import com.tans.tweather2.entites.City
import com.tans.tweather2.entites.Weather
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(
        private val weatherService: WeatherService,
        private val weatherDao: WeatherDao,
        private val citiesRepository: CitiesRepository
) {

    fun getWeather(city: City): Observable<Weather> = Maybe.concat<Weather>(listOf(getWeatherLocal(city),
            getWeatherRemote(city).toMaybe()))
            .toObservable()

    fun getWeather(lat: Double, long: Double)
            : Single<Weather> = weatherService
            .getWeather(WeatherService.Companion.WeatherRequest.CoordinateRequest(lat = lat, long = long))

    fun getWeatherRemote(city: City): Single<Weather> = when {
        city.woeid != -1L -> {
            weatherService.getWeather(WeatherService.Companion.WeatherRequest.WoeidReqeust(woeid = city.woeid))
        }

        (city.lat != -1.0 && city.lon != -1.0) -> {
            weatherService.getWeather(WeatherService.Companion.WeatherRequest.CoordinateRequest(lat = city.lat, long = city.lon))
        }

        else -> {
            weatherService.getWeather(WeatherService.Companion.WeatherRequest.CityNameRequest(cityName = city.cityName))
        }

    }.flatMap { weather ->
        val fixedWeather = weather.copy(woeid = weather.location.woeid)
        val fixedCity = city.copy(woeid = weather.location.woeid,
                lat = weather.location.lat,
                lon = weather.location.long)
        Completable.merge(listOf(updateCityLocal(city = fixedCity), updateWeatherLocal(fixedWeather)))
                .toSingleDefault(fixedWeather)
    }

    fun getWeatherLocal(city: City): Maybe<Weather> = if (city.woeid == -1L) {
        Maybe.empty()
    } else {
        weatherDao.queryWeatherByWoeid(city.woeid)
    }

    private fun updateWeatherLocal(weather: Weather): Completable = weatherDao.insert(weather)

    private fun updateCityLocal(city: City): Completable {
        return citiesRepository.updateCity(city)
    }

}