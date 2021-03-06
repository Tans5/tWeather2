package com.tans.tweather2.di.module

import androidx.room.Room
import android.content.Context
import com.tans.tweather2.TWeatherApplication
import com.tans.tweather2.api.ApiClient
import com.tans.tweather2.api.service.BingService
import com.tans.tweather2.api.service.CitiesService
import com.tans.tweather2.api.service.WeatherService
import com.tans.tweather2.db.TWeatherDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideBaseContext(app: TWeatherApplication): Context = app.baseContext


    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService = ApiClient
            .retrofitClientBuilder(ApiClient.ClientType.Weather)
            .build()
            .create(WeatherService::class.java)

    @Singleton
    @Provides
    fun provideCitiesService(): CitiesService = ApiClient
            .retrofitClientBuilder(ApiClient.ClientType.City)
            .build()
            .create(CitiesService::class.java)

    @Singleton
    @Provides
    fun provideBingService(): BingService = ApiClient
            .retrofitClientBuilder(ApiClient.ClientType.Bing)
            .build()
            .create(BingService::class.java)

    @Singleton
    @Provides
    fun provideDb(context: Context): TWeatherDb = Room
            .databaseBuilder(context, TWeatherDb::class.java, "tweather2.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideCityDao(db: TWeatherDb) = db.getCityDao()

    @Singleton
    @Provides
    fun provideWeatherDao(db: TWeatherDb) = db.getWeatherDao()

    @Singleton
    @Provides
    fun provideImagesDao(db: TWeatherDb) = db.getImageDao()
}