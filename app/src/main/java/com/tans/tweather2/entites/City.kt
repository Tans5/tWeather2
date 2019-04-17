package com.tans.tweather2.entites

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tans.tweather2.db.RoomConverter

@Entity(primaryKeys = ["id"], tableName = "city")
@TypeConverters(RoomConverter::class)
@JsonClass(generateAdapter = true)
data class City(
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "parent_id") val parentId: Long = -1,
        @ColumnInfo(name = "level") val level: Int = -1,
        @Json(name = "city_name") @ColumnInfo(name = "city_name") val cityName: String,
        @ColumnInfo(name = "lat") val lat: Double = -1.0,
        @ColumnInfo(name = "lon") val lon: Double = -1.0,
        @ColumnInfo(name = "woeid") val woeid: Long = -1,
        @ColumnInfo(name = "favor_order") val favorOrder: Int = -1)