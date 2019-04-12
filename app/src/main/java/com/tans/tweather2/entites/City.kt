package com.tans.tweather2.entites

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.tans.tweather2.db.RoomConverter

@Entity(primaryKeys = ["id"], tableName = "city")
@TypeConverters(RoomConverter::class)
data class City(
        @ColumnInfo(name = "id") val id: Long,
        @Nullable @ColumnInfo(name = "parent_id") val parentId: Long?,
        @ColumnInfo(name = "level") val level: Int = 0,
        @Json(name = "city_name") @ColumnInfo(name = "city_name") val cityName: String,
        @Nullable @ColumnInfo(name = "lat") val lat: Double?,
        @Nullable @ColumnInfo(name = "lon") val lon: Double?,
        @ColumnInfo(name = "favor_order") val favorOrder: Int = -1)