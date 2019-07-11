package com.tstv.weatherapp.repository

import com.tstv.weatherapp.data.db.SearchCityRecentQueriesDao
import com.tstv.weatherapp.data.network.ApixuWeatherApiService
import com.tstv.weatherapp.data.network.response.ForecastHourlyResponse
import com.tstv.weatherapp.data.network.response.ForecastResponse
import com.tstv.weatherapp.data.network.response.vo.City
import com.tstv.weatherapp.internal.UnitSystem
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepository @Inject constructor(
    private val weatherApiService: ApixuWeatherApiService,
    private val searchCityRecentQueriesDao: SearchCityRecentQueriesDao
){

    suspend fun getWeatherAsync(location: String, units: UnitSystem): Deferred<ForecastResponse> {
        return withContext(Dispatchers.IO) {
            weatherApiService.getForecast(location, units.name)
        }
    }

    suspend fun getWeatherByHoursAsync(location: String, units: UnitSystem): Deferred<ForecastHourlyResponse> {
        return withContext(Dispatchers.IO) {
            weatherApiService.getForecastByHour(location, units.name)
        }
    }

    suspend fun getRecentQueries(): List<City>{
        return withContext(Dispatchers.IO){
            searchCityRecentQueriesDao.getAllRecentQueries()
        }
    }

    suspend fun removeRecentQuery(query: String){
        return withContext(Dispatchers.IO){
            searchCityRecentQueriesDao.removeRecentQuery(query.toLowerCase())
        }
    }

    suspend fun addRecentQuery(query: City){
        return withContext(Dispatchers.IO){
            searchCityRecentQueriesDao.insert(query)
        }
    }
}