package com.tstv.weatherapp.data.network.response.vo


data class City(
    val coord: Coord?,
    val country: String,
    val id: Int?,
    val name: String,
    val population: Int?,
    val timezone: Int
)