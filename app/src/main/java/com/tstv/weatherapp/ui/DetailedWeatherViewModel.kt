package com.tstv.weatherapp.ui

import androidx.lifecycle.ViewModel
import com.tstv.weatherapp.repository.WeatherRepository
import javax.inject.Inject

class DetailedWeatherViewModel @Inject constructor(
    repository: WeatherRepository
) : ViewModel() {
}