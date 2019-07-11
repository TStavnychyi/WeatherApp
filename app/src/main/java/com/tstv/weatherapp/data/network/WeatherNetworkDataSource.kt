package com.tstv.weatherapp.data.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNetworkDataSource @Inject constructor(
    private val apiService: WeatherApiService
){

//    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
//
//    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
//        get() = _downloadedCurrentWeather
//
//    suspend fun fetchCurrentWeather(location: String, languageCode: String) {
//        try {
//            val fetchedCurrentWeather = apiService
//                .getAllRecentQueries(location, languageCode)
//                .await()
//
//            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
//        }catch (e: NoConnectivityException)
//        {
//            Log.e("Connectivity", "No internet connection.", e)
//        }
//    }
}