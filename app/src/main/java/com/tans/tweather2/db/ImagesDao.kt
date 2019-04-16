package com.tans.tweather2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tans.tweather2.entites.Images
import io.reactivex.Maybe

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(images: Images)

    @Query("select * from images where date_string = :dateString")
    fun queryImagesByDateString(dateString: String): Maybe<Images>

}