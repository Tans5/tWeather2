package com.tans.tweather2.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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