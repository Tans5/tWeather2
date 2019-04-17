package com.tans.tweather2.api.service

import com.tans.tweather2.entites.Images
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BingService {

    /**
     * @param idx: the day of images, 0 is today.
     * @param n: the size of image.
     * @param format: Json or xml.
     *
     */
    @GET("HPImageArchive.aspx")
    fun getImages(@Query("idx") idx: Int = 0,
                  @Query("n") n: Int = 1,
                  @Query("format") format: String = "js"): Single<Images>

}