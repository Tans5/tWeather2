package com.tans.tweather2.entites

import com.google.gson.annotations.SerializedName

data class Wind(
        @field:SerializedName("chill")
        val chill: Int,
        @field:SerializedName("direction")
        val direction: Int,
        @field:SerializedName("speed")
        val speed: Int)