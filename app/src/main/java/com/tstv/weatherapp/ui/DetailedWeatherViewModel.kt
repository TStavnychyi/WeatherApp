package com.tstv.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tstv.weatherapp.data.network.response.WeatherResponse
import com.tstv.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailedWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weather =  MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse>
        get() = _weather

    fun loadWeather(location: String){
        viewModelScope.launch {
            _weather.postValue(weatherRepository.getWeatherAsync(location).await())
        }
    }
}