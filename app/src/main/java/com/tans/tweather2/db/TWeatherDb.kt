package com.tans.tweather2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tans.tweather2.entites.City
import com.tans.tweather2.entites.Images
import com.tans.tweather2.entites.Weather

@Database(
        entities = [City::class, Weather::class, Images::class],
        version = 1,
        exportSchema = false
)
abstract class TWeatherDb : RoomDatabase() {
    abstract fun getCityDao(): CityDao

    abstract fun getWeatherDao(): WeatherDao

    abstract fun getImageDao(): ImagesDao
}