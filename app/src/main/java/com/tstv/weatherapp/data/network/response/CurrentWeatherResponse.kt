package com.tstv.weatherapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.tstv.weatherapp.data.db.entity.CurrentWeatherEntry
import com.tstv.weatherapp.data.db.entity.WeatherLocation

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation
)