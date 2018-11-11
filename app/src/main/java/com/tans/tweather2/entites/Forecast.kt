package com.tans.tweather2.entites

import com.google.gson.annotations.SerializedName

data class Forecast(@field:SerializedName("channel") val forecastItems: List<ForecastItem>)

data class ForecastItem(@field:SerializedName("item") val item: ForecastItem1)
data class ForecastItem1(@field:SerializedName("forecast") val forecast: ForecastItem2)
data class ForecastItem2(
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