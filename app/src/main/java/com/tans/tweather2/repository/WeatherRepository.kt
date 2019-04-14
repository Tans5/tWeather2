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
        city.woeid != null -> {
            weatherService.getWeather(WeatherService.Companion.WeatherRequest.WoeidReqeust(woeid = city.woeid))
        }

        (city.lat != null && city.lon != null) -> {
            weatherService.getWeather(WeatherService.Companion.WeatherRequest.CoordinateRequest(lat = city.lat, long = city.lon))
        }

        else -> {
            weatherService.getWeather(WeatherService.Companion.WeatherRequest.CityNameRequest(cityName = city.cityName))
        }

    }.flatMap { weather ->
        Completable.merge(listOf(updateCityLocal(weather = weather, city = city), updateWeatherLocal(weather)))
                .toSingleDefault(weather)
    }

    fun getWeatherLocal(city: City): Maybe<Weather> = if (city.woeid == null) {
        Maybe.empty()
    } else {
        weatherDao.queryWeatherByWoeid(city.woeid.toString())
    }

    private fun updateWeatherLocal(weather: Weather): Completable = Completable.fromAction {
        val fixedWeather = weather.copy(woeid = weather.location.woeid)
        weatherDao.insert(fixedWeather)
    }

    private fun updateCityLocal(weather: Weather, city: City): Completable {
        val fixedCity = city.copy(woeid = weather.location.woeid,
                lat = weather.location.lat,
                lon = weather.location.long)
        return citiesRepository.updateCity(fixedCity)
    }

}