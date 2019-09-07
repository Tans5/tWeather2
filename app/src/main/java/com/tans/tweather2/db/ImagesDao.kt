package com.tans.tweather2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tans.tweather2.entites.Images
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(images: Images): Completable

    @Query("select * from images where date_long = :dateLong")
    fun queryImagesByDateString(dateLong: Long): Maybe<Images>

    @Query("select * from images where date_long = (select max(date_long) from images)")
    fun queryLatestImages(): Maybe<Images>

}