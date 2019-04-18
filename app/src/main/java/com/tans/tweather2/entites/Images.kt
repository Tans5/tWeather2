package com.tans.tweather2.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tans.tweather2.db.RoomConverter

@Entity(tableName = "images", primaryKeys = ["date_long"])
@TypeConverters(RoomConverter::class)
@JsonClass(generateAdapter = true)
data class Images(@ColumnInfo(name = "date_long") val dateLong: Long = -1,
                  val images: List<Image>,
                  @ColumnInfo(name = "tool_tips") @Json(name = "tooltips") val toolTips: ToolTips)

@JsonClass(generateAdapter = true)
data class ToolTips(val loading: String,
                    val previous: String,
                    val next: String,
                    val walle: String,
                    val walls: String)

@JsonClass(generateAdapter = true)
data class Image(@Json(name = "startdate") val startDate: String,
                 @Json(name = "fullstartdate") val fullStartDate: String,
                 @Json(name = "enddate") val endDate: String,
                 val url: String,
                 @Json(name = "urlbase") val urlBase: String,
                 @Json(name = "copyright") val copyRight: String,
                 @Json(name = "copyrightlink") val copyRightLink: String,
                 val title: String,
                 val quiz: String,
                 val wp: Boolean,
                 val hsh: String,
                 val drk: Int,
                 val top: Int,
                 val bot: Int)