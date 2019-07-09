package com.tstv.weatherapp.di.modules

import android.app.Application
import com.tstv.weatherapp.data.db.CurrentWeatherDao
import com.tstv.weatherapp.data.db.WeatherDatabase
import com.tstv.weatherapp.data.network.ApixuWeatherApiService
import com.tstv.weatherapp.data.network.interceptor.ConnectivityInterceptor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideConnectivityInterceptor(app: Application): ConnectivityInterceptor{
        return ConnectivityInterceptor(app)
    }

    @Singleton
    @Provides
    fun provideWeatherService(interceptor: ConnectivityInterceptor): ApixuWeatherApiService {
        return ApixuWeatherApiService(interceptor)
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): WeatherDatabase {
        return WeatherDatabase.invoke(app)
    }

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(weatherDb: WeatherDatabase): CurrentWeatherDao{
        return weatherDb.currentWeatherDao()
    }

}