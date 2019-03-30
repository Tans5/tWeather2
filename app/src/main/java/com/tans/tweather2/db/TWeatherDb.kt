package com.tans.tweather2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tans.tweather2.entites.City

@Database(
        entities = [City::class],
        version = 1,
        exportSchema = false
)

abstract class TWeatherDb : RoomDatabase() {
    abstract fun getCityDao(): CityDao
}