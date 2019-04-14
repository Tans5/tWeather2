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
        @Nullable @ColumnInfo(name = "parent_id") val parentId: Long? = null,
        @ColumnInfo(name = "level") val level: Int = -1,
        @Json(name = "city_name") @ColumnInfo(name = "city_name") val cityName: String,
        @Nullable @ColumnInfo(name = "lat") val lat: Double? = null,
        @Nullable @ColumnInfo(name = "lon") val lon: Double? = null,
        @Nullable @ColumnInfo(name = "woeid") val woeid: Long? = null,
        @ColumnInfo(name = "favor_order") val favorOrder: Int = -1)