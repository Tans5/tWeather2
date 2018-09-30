package com.tans.tweather2.entites

import com.google.gson.annotations.SerializedName

data class ForecastItem(
        @field:SerializedName("code")
        val code: Int,
        @field:SerializedName("date")
        val date: String,
        @field:SerializedName("day")
        val day: String,
        @field:SerializedName("high")
        val high: Int,
        @field:SerializedName("low")
        val low: Int,
        @field:SerializedName("text")
        val text: String
)