package com.tans.tweather2.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.tans.tweather2.entites.City

@Database(
        entities = [City::class],
        version = 1,
        exportSchema = false
)

abstract class TWeatherDb : RoomDatabase() {
    abstract fun getCityDao(): CityDao
}