package com.tans.tweather2.entites

import com.google.gson.annotations.SerializedName

data class Forecast(@field:SerializedName("channel") val channel: List<ForecastItem>)