package com.tstv.weatherapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.tstv.weatherapp.data.db.entity.WeatherEntry
import com.tstv.weatherapp.data.db.entity.WeatherLocation


data class CurrentWeatherResponse(
    @SerializedName("current")
    val weatherEntry: WeatherEntry,
    val location: WeatherLocation
)