package com.tans.tweather2.entites

import com.google.gson.annotations.SerializedName

data class Atmosphere(
        @field:SerializedName("humidity")
        val humidity: Int,
        @field:SerializedName("pressure")
        val pressure: Float,
        @field:SerializedName("rising")
        val rising: Int,
        @field:SerializedName("visibility")
        val visibility: Float)