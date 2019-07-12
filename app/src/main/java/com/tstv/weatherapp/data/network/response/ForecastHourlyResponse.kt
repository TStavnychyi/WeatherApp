package com.tstv.weatherapp.data.network.response


import com.tstv.weatherapp.data.network.response.vo.City
import com.tstv.weatherapp.data.network.response.vo.Day
import com.tstv.weatherapp.data.network.response.vo.DayHourly
import com.tstv.weatherapp.data.network.response.vo.ErrorStatus

data class ForecastHourlyResponse(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<DayHourly>,
    val message: Double?,
    var error: ErrorStatus? = null
){
    constructor(error: ErrorStatus): this(City(null, ""), null, "", listOf<DayHourly>(), null, error)
}