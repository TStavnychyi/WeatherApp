package com.tstv.weatherapp.data.network.response

import com.tstv.weatherapp.data.network.response.vo.City
import com.tstv.weatherapp.data.network.response.vo.Day


data class ForecastResponse(
    val city: City,
    val cnt: Int?,
    val cod: String,
    val list: List<Day>,
    val message: Double?,
    var error: Throwable
){
    constructor(error: Throwable): this(City(null, ""), null, "", listOf<Day>(), null, error)
}