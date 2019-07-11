package com.tstv.weatherapp.repository

import com.tstv.weatherapp.data.db.SearchCityRecentQueriesDao
import com.tstv.weatherapp.data.network.WeatherApiService
import com.tstv.weatherapp.data.network.response.ForecastHourlyResponse
import com.tstv.weatherapp.data.network.response.ForecastResponse
import com.tstv.weatherapp.data.network.response.vo.City
import com.tstv.weatherapp.internal.UnitSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepository @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val searchCityRecentQueriesDao: SearchCityRecentQueriesDao
){

    suspend fun getWeatherAsync(location: String, units: UnitSystem): ForecastResponse {
        return withContext(Dispatchers.IO) {
            var apiResponse: ForecastResponse? = null
            weatherApiService.getForecast(location, units.name).enqueue(object : Callback<ForecastResponse>{
                override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                    apiResponse = ForecastResponse(t)
                }

                override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                    if(response.isSuccessful){
                        apiResponse = response.body()
                    }
                }
            })
            apiResponse!!
        }
    }

    suspend fun getWeatherByHoursAsync(location: String, units: UnitSystem): ForecastHourlyResponse {
        return withContext(Dispatchers.IO) {
            var apiResponse: ForecastHourlyResponse? = null
            weatherApiService.getForecastByHour(location, units.name).enqueue(object : Callback<ForecastHourlyResponse>{
                override fun onFailure(call: Call<ForecastHourlyResponse>, t: Throwable) {
                    apiResponse = ForecastHourlyResponse(t)
                }

                override fun onResponse(call: Call<ForecastHourlyResponse>, response: Response<ForecastHourlyResponse>) {
                    if(response.isSuccessful){
                        apiResponse = response.body()
                    }
                }
            })
            apiResponse!!
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