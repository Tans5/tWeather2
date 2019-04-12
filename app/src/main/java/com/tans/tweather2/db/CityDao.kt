package com.tans.tweather2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tans.tweather2.entites.City
import io.reactivex.Single

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Query("select * from city where id = :id")
    fun queryById(id: String): Single<List<City>>

    @Query("select * from city where parent_id = :parentId")
    fun queryByParentId(parentId: String): Single<List<City>>

    @Query("select count(*) from city where favor_order > 0")
    fun favorCitySize(): Single<Int>

    @Query("select * from city where favor_order > 0 order by favor_order")
    fun queryFavorCities(): Single<List<City>>
}