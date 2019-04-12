package com.tans.tweather2.db

import androidx.room.TypeConverter

object RoomConverter {

    @TypeConverter
    @JvmStatic
    fun intToString(value: Int?): String? = value?.toString()

    @TypeConverter
    @JvmStatic
    fun stringToInt(value: String?): Int? = value?.toInt()

    @TypeConverter
    @JvmStatic
    fun longToString(value: Long?): String? = value?.toString()

    @TypeConverter
    @JvmStatic
    fun stringToLong(value: String?): Long? = value?.toLong()

    @TypeConverter
    @JvmStatic
    fun floatToString(value: Float?): String? = value?.toString()

    @TypeConverter
    @JvmStatic
    fun stringToFloat(value: String?): Float? = value?.toFloat()

    @TypeConverter
    @JvmStatic
    fun doubleToString(value: Double?): String? = value?.toString()

    @TypeConverter
    @JvmStatic
    fun stringToDouble(value: String?): Double? = value?.toDouble()
}