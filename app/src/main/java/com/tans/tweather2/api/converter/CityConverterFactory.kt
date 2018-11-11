package com.tans.tweather2.api.converter

import com.tans.tweather2.api.ApiResponse
import com.tans.tweather2.api.service.Cities
import com.tans.tweather2.entites.City
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object CityConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit)
            : Converter<ResponseBody, *>? {
        return if (getRawType(type) == ApiResponse::class.java) {
            when (getParameterUpperBound(0, type as ParameterizedType)) {
                List::class.java -> CityConverter
                else -> super.responseBodyConverter(type, annotations, retrofit)
            }
        } else {
            super.responseBodyConverter(type, annotations, retrofit)
        }
    }

}

object CityConverter : Converter<ResponseBody, ApiResponse<Cities>> {
    override fun convert(value: ResponseBody): ApiResponse<Cities> {
        val citesString = value.string()
        val cities = citesString.split(",").map {
            val cityString = it.split("|")
            val cityCode = cityString[0]
            val cityName = cityString[1]
            val parentCode = cityCode.subSequence(startIndex = cityCode.length - 2,
                    endIndex = cityCode.length).toString()
            val level = cityCode.length / 2
            City(id = cityCode,
                    parentId = parentCode,
                    level = level,
                    cityName = cityName)
        }
        return if (cities.isEmpty()) {
            ApiResponse.ApiEmptyResponse()
        } else {
            ApiResponse.ApiSuccessResponse(cities)
        }
    }

}