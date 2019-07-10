package com.tstv.weatherapp.data.network.response


import com.tstv.weatherapp.data.network.response.vo.City
import com.tstv.weatherapp.data.network.response.vo.DayHourly

data class ForecastHourlyResponse(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<DayHourly>,
    val message: Double?
)