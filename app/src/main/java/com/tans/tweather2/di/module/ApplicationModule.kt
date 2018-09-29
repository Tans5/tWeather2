package com.tans.tweather2.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.tans.tweather2.TWeatherApplication
import com.tans.tweather2.api.ApiClient
import com.tans.tweather2.api.service.CitiesService
import com.tans.tweather2.api.service.LocationService
import com.tans.tweather2.api.service.WeatherService
import com.tans.tweather2.db.TWeatherDb
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideBaseContext(app: TWeatherApplication): Context = app.baseContext

    @Named("location")
    @Singleton
    @Provides
    fun provideLocationApiClientBuilder(): Retrofit.Builder = ApiClient.locationApiClientBuilder()

    @Named("weather")
    @Singleton
    @Provides
    fun provideWeatherApiClientBuilder(): Retrofit.Builder = ApiClient.weatherApiClientBuilder()

    @Named("cities")
    @Singleton
    @Provides
    fun provideCitiesApiClientBuilder(): Retrofit.Builder = ApiClient.citesApiClientBuilder()

    @Singleton
    @Provides
    fun provideLocationService(@Named("location") builder: Retrofit.Builder): LocationService
            = builder.build().create(LocationService::class.java)

    @Singleton
    @Provides
    fun provideWeatherService(@Named("weather") builder: Retrofit.Builder): WeatherService
            = builder.build().create(WeatherService::class.java)

    @Singleton
    @Provides
    fun provideCitiesService(@Named("cities") builder: Retrofit.Builder): CitiesService
            = builder.build().create(CitiesService::class.java)

    @Singleton
    @Provides
    fun provideDb(context: Context): TWeatherDb = Room.databaseBuilder(context, TWeatherDb::class.java, "tweather2.db")
            .build()

    @Singleton
    @Provides
    fun provideCityDao(db: TWeatherDb) = db.getCityDao()
}