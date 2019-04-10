package com.tans.tweather2.di.module

import androidx.room.Room
import android.content.Context
import com.tans.tweather2.TWeatherApplication
import com.tans.tweather2.api.ApiClient
import com.tans.tweather2.api.service.CitiesService
import com.tans.tweather2.api.service.LocationService
import com.tans.tweather2.api.service.WeatherService2
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
    fun provideLocationService(): LocationService = ApiClient
            .retrofitClientBuilder(ApiClient.ClientType.Location)
            .build()
            .create(LocationService::class.java)

    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService2 = ApiClient
            .retrofitClientBuilder(ApiClient.ClientType.Weather)
            .build()
            .create(WeatherService2::class.java)

    @Singleton
    @Provides
    fun provideCitiesService(): CitiesService = ApiClient
            .retrofitClientBuilder(ApiClient.ClientType.City)
            .build()
            .create(CitiesService::class.java)

    @Singleton
    @Provides
    fun provideDb(context: Context): TWeatherDb = Room
            .databaseBuilder(context, TWeatherDb::class.java, "tweather2.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideCityDao(db: TWeatherDb) = db.getCityDao()
}