package com.tstv.weatherapp.ui.weather_detailed

import androidx.lifecycle.*
import com.tstv.weatherapp.R
import com.tstv.weatherapp.data.network.response.ForecastHourlyResponse
import com.tstv.weatherapp.data.network.response.ForecastResponse
import com.tstv.weatherapp.data.provider.UnitProvider
import com.tstv.weatherapp.internal.UnitSystem
import com.tstv.weatherapp.internal.UnitSystem.METRIC
import com.tstv.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.isActive
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
            _weather.postValue(weatherRepository.getWeatherAsync(location, units))
        }
    }

    fun loadWeatherByHour(location: String, units: UnitSystem = METRIC){
        viewModelScope.launch {
            _weatherByHour.postValue(weatherRepository.getWeatherByHoursAsync(location, units))
        }
    }

    fun getIsDay(): Boolean{
        val localTime = LocalTime.now()
        return localTime.hour in 6..22
    }

    fun getUnitSystem() = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = getUnitSystem() == METRIC

    fun retry(location: String, units: UnitSystem = METRIC){
        if(viewModelScope.isActive) {
            loadWeather(location, units)
            loadWeatherByHour(location, units)
        }
    }

    fun removeRecentQuery(query: String){
        viewModelScope.launch {
            weatherRepository.removeRecentQuery(query.toLowerCase().capitalize())
        }
    }

    fun getTextColorFromTemperature(temperature: Int, units: UnitSystem): Int{
        return if(units == METRIC){
            when {
                temperature < 10 -> R.color.temperature_cold
                temperature in 10..20 -> R.color.temperature_medium
                else -> R.color.temperature_hot
            }
        }else{
            when {
                temperature < 50 -> R.color.temperature_cold
                temperature in 50..68 -> R.color.temperature_medium
                else -> R.color.temperature_hot
            }
        }
    }

}