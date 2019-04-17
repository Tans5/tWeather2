package com.tans.tweather2.repository

import com.tans.tweather2.api.service.BingService
import com.tans.tweather2.db.ImagesDao
import com.tans.tweather2.entites.Images
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ImagesRepository @Inject constructor(private val bingService: BingService,
                                           private val imagesDao: ImagesDao) {



    fun getImagesRemote(): Single<Images> = bingService.getImages()
            .zipWith(formatImageDateString(Date()))
            .map { (images, dateString) ->
                images.copy(dateString = dateString)
            }
            .flatMap { images ->
                insertImages(images).toSingleDefault(images)
            }


    fun getTodayImagesLocal(): Maybe<Images> = formatImageDateString(Date())
            .flatMapMaybe { todayDateString -> imagesDao.queryImagesByDateString(todayDateString) }

    private fun insertImages(images: Images): Completable = Completable.fromCallable {
        imagesDao.insert(images = images)
    }

    /**
     * eg: 20190418
     */
    private fun formatImageDateString(date: Date): Single<String> = Single.fromCallable {
        SimpleDateFormat("yyyyMMdd").format(date)
    }
}