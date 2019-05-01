package com.tans.tweather2.db

import androidx.room.TypeConverter
import com.squareup.moshi.Types
import com.tans.tweather2.api.ApiClient
import com.tans.tweather2.entites.*

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


    @TypeConverter
    @JvmStatic
    fun weatherToString(value: Weather?)
            : String? = WeatherJsonAdapter(ApiClient.moshi).toJson(value)

    @TypeConverter
    @JvmStatic
    fun stringToWeather(value: String?)
            : Weather? = WeatherJsonAdapter(ApiClient.moshi).fromJson(value ?: "")

    @TypeConverter
    @JvmStatic
    fun locationToString(value: Location?)
            : String? = LocationJsonAdapter(ApiClient.moshi).toJson(value)

    @TypeConverter
    @JvmStatic
    fun stringToLocation(value: String?)
            : Location? = LocationJsonAdapter(ApiClient.moshi).fromJson(value ?: "")

    @TypeConverter
    @JvmStatic
    fun currentObservationToString(value: CurrentObservation?)
            : String? = CurrentObservationJsonAdapter(ApiClient.moshi).toJson(value)

    @TypeConverter
    @JvmStatic
    fun stringToCurrentObservation(value: String?)
            : CurrentObservation? = CurrentObservationJsonAdapter(ApiClient.moshi).fromJson(value ?: "")

    @TypeConverter
    @JvmStatic
    fun forecastsToString(value: List<Forecast>?)
            : String? = ApiClient.moshi.adapter<List<Forecast>>(Types.newParameterizedType(List::class.java, Forecast::class.java)).toJson(value)

    @TypeConverter
    @JvmStatic
    fun stringToForecasts(value: String?)
            : List<Forecast>? = ApiClient.moshi.adapter<List<Forecast>>(Types.newParameterizedType(List::class.java, Forecast::class.java)).fromJson(value ?: "")

    @TypeConverter
    @JvmStatic
    fun imagesToString(value: List<Image>?)
            : String? = ApiClient.moshi.adapter<List<Image>>(Types.newParameterizedType(List::class.java, Image::class.java)).toJson(value)

    @TypeConverter
    @JvmStatic
    fun stringToImages(value: String?)
            : List<Image>? = ApiClient.moshi.adapter<List<Image>>(Types.newParameterizedType(List::class.java, Image::class.java)).fromJson(value ?: "")

    @TypeConverter
    @JvmStatic
    fun stringToToolTops(value: String?)
            : ToolTips? = ToolTipsJsonAdapter(ApiClient.moshi).fromJson(value ?: "")

    @TypeConverter
    @JvmStatic
    fun toolTopsToString(value: ToolTips?)
            : String? = ToolTipsJsonAdapter(ApiClient.moshi).toJson(value)

}