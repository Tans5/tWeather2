package com.tans.tweather2.entites

import com.google.gson.annotations.SerializedName

data class Condition(
        @field:SerializedName("code")
        val code: Int,
        @field:SerializedName("date")
        val date: String,
        @field:SerializedName("temp")
        val temp: Int,
        @field:SerializedName("text")
        val text: String)