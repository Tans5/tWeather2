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

    @Query("select * from city where parentId = :parentId")
    fun queryByParentId(parentId: String): Single<List<City>>
}