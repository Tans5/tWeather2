package com.tans.tweather2.entites

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"], tableName = "city")
data class City(
        @field:SerializedName("id")
        val id: String,
        @field:SerializedName("parentId")
        val parentId: String = "",
        @field:SerializedName("level")
        val level: Int,
        @field:SerializedName("city_name")
        val cityName: String)