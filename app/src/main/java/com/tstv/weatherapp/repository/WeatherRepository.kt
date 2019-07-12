package com.tstv.weatherapp.repository

import com.tstv.weatherapp.data.db.SearchCityRecentQueriesDao
import com.tstv.weatherapp.data.network.WeatherApiService
import com.tstv.weatherapp.data.network.response.ForecastHourlyResponse
import com.tstv.weatherapp.data.network.response.ForecastResponse
import com.tstv.weatherapp.data.network.response.vo.City
import com.tstv.weatherapp.data.network.response.vo.ErrorStatus
import com.tstv.weatherapp.internal.NoConnectivityException
import com.tstv.weatherapp.internal.UnitSystem
import com.tstv.weatherapp.internal.getErrorStatusFromErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepository @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val searchCityRecentQueriesDao: SearchCityRecentQueriesDao
){

    suspend fun getWeatherAsync(location: String, units: UnitSystem): ForecastResponse {
        return withContext(Dispatchers.IO) {
            var result: ForecastResponse? = null
            val deferredWeather = weatherApiService.getForecast(location, units.name)

            result = try{
                deferredWeather.await()
            }catch (error: Exception){
                val errorStatus = parseRetrofitError(error)
                ForecastResponse(errorStatus)
            }
            joinAll()
            return@withContext result!!
        }
    }

    suspend fun getWeatherByHoursAsync(location: String, units: UnitSystem): ForecastHourlyResponse {
        return withContext(Dispatchers.IO) {
            var result: ForecastHourlyResponse? = null
            val deferredWeather = weatherApiService.getForecastByHour(location, units.name)

            result = try{
                deferredWeather.await()
            }catch (error: Exception){
                val errorStatus = parseRetrofitError(error)
                ForecastHourlyResponse(errorStatus)
            }
            joinAll()
            return@withContext result!!
        }
    }

    suspend fun getRecentQueries(): List<City>{
        return withContext(Dispatchers.IO){
            searchCityRecentQueriesDao.getAllRecentQueries()
        }
    }

    suspend fun removeRecentQuery(query: String){
        return withContext(Dispatchers.IO){
            searchCityRecentQueriesDao.removeRecentQuery(query)
        }
    }

    suspend fun addRecentQuery(query: City){
        return withContext(Dispatchers.IO){
            searchCityRecentQueriesDao.insert(query)
        }
    }

    private fun parseRetrofitError(error: Throwable): ErrorStatus{
        var errorStatus = ErrorStatus.OTHER
        if(error is HttpException){
            errorStatus = getErrorStatusFromErrorCode(error.response().code())
        }else if(error is NoConnectivityException){
            errorStatus = ErrorStatus.NO_INTERNET
        }
        return errorStatus
    }
}