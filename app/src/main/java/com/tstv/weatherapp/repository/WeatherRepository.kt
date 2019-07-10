package com.tstv.weatherapp.repository

import com.tstv.weatherapp.data.db.CurrentWeatherDao
import com.tstv.weatherapp.data.network.ApixuWeatherApiService
import com.tstv.weatherapp.data.network.response.ForecastHourlyResponse
import com.tstv.weatherapp.data.network.response.ForecastResponse
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

    suspend fun getWeatherAsync(location: String): Deferred<ForecastResponse> {
        return withContext(Dispatchers.IO) {
            weatherApiService.getForecast(location)
        }
    }

    suspend fun getWeatherByHoursAsync(location: String): Deferred<ForecastHourlyResponse> {
        return withContext(Dispatchers.IO) {
            weatherApiService.getForecastByHour(location)
        }
    }
}