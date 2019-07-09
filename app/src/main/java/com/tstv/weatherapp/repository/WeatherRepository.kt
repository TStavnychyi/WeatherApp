package com.tstv.weatherapp.repository

import com.tstv.weatherapp.data.db.CurrentWeatherDao
import com.tstv.weatherapp.data.network.ApixuWeatherApiService
import com.tstv.weatherapp.data.network.response.WeatherResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepository @Inject constructor(
    private val weatherApiService: ApixuWeatherApiService,
    private val currentWeatherDao: CurrentWeatherDao
){

    suspend fun getWeatherAsync(location: String): Deferred<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            weatherApiService.getFutureWeather(location)
        }
    }
}