package com.tans.tweather2.api.service

import com.tans.tweather2.api.ApiResponse
import com.tans.tweather2.entites.City
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

typealias Cities = List<City>

interface CitiesService {

    @GET("data/list3/city{parentCode}.xml")
    fun getCities(@Path("parentCode") parentCode: String = ""): Single<ApiResponse<Cities>>
}