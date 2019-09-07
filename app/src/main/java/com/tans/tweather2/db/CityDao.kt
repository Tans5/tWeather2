package com.tans.tweather2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tans.tweather2.api.service.Cities
import com.tans.tweather2.entites.City
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cities: Cities): Completable

    @Query("select * from city where id = :id")
    fun queryById(id: String): Maybe<Cities>

    @Query("select * from city where parent_id = :parentId")
    fun queryByParentId(parentId: String): Maybe<Cities>

    @Query("select * from city where parent_id = -1")
    fun queryRootCites(): Maybe<Cities>

    @Query("select count(*) from city where favor_order > 0")
    fun favorCitySize(): Maybe<Int>

    @Query("select * from city where favor_order > 0 order by favor_order")
    fun queryFavorCities(): Maybe<Cities>

    @Query("select max(favor_order) from city where favor_order > 0")
    fun maxFavorOrder(): Maybe<Int>
}