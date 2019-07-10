package com.tstv.weatherapp.data.network.response.vo


data class Day(
    val clouds: Int?,
    val deg: Int?,
    val dt: Long?,
    val humidity: Double,
    val pressure: Double,
    val rain: Double?,
    val speed: Double?,
    val temp: Temp,
    val weather: List<Weather?>
): IDay