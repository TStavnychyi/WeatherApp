package com.tstv.weatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tstv.weatherapp.data.network.response.CurrentWeatherResponse
import com.tstv.weatherapp.internal.NoConnectivityException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNetworkDataSource @Inject constructor(
    private val apiService: ApixuWeatherApiService
){

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = apiService
                .getCurrentWeather(location, languageCode)
                .await()

            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }catch (e: NoConnectivityException)
        {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}