package com.tstv.weatherapp.data.network.response.vo


import com.google.gson.annotations.SerializedName

data class DayHourly(
    val clouds: Clouds?,
    val dt: Long?,
    @SerializedName("dt_txt")
    val dtTxt: String?,
    val main: Main,
    val weather: List<Weather?>,
    val wind: Wind?
): IDay