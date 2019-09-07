package com.tans.tweather2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tans.tweather2.entites.Weather
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface WeatherDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insert(weather: Weather): Completable

    @Query("select * from weather where woeid = :woeid")
    fun queryWeatherByWoeid(woeid: Long): Maybe<Weather>
}