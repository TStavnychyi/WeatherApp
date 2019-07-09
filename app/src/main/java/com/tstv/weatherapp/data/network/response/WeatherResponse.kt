package com.tstv.weatherapp.data.network.response

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)