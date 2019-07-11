package com.tstv.weatherapp.ui.weather_detailed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tstv.weatherapp.data.network.response.ForecastHourlyResponse
import com.tstv.weatherapp.data.network.response.ForecastResponse
import com.tstv.weatherapp.data.provider.UnitProvider
import com.tstv.weatherapp.internal.UnitSystem
import com.tstv.weatherapp.internal.UnitSystem.*
import com.tstv.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import org.threeten.bp.LocalTime
import javax.inject.Inject

class DetailedWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val unitProvider: UnitProvider
) : ViewModel() {

    private val _weather =  MutableLiveData<ForecastResponse>()
    val weather: LiveData<ForecastResponse>
        get() = _weather

    private val _weatherByHour =  MutableLiveData<ForecastHourlyResponse>()
    val weatherByHour: LiveData<ForecastHourlyResponse>
        get() = _weatherByHour


    fun loadWeather(location: String, units: UnitSystem = METRIC){
        viewModelScope.launch {
            _weather.postValue(weatherRepository.getWeatherAsync(location, units).await())
        }
    }

    fun loadWeatherByHour(location: String, units: UnitSystem = METRIC){
        viewModelScope.launch {
            _weatherByHour.postValue(weatherRepository.getWeatherByHoursAsync(location, units).await())
        }
    }

    fun getIsDay(): Boolean{
        val localTime = LocalTime.now()
        return localTime.hour in 6..22
    }

    fun getUnitSystem() = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = getUnitSystem() == METRIC
}