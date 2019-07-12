package com.tstv.weatherapp.data.network.response.vo

enum class ErrorStatus{
    INVALID_API_KEY,
    CITY_NOT_FOUND,
    API_KEY_BLOCKED,
    INTERNAL_SERVER_ERROR,
    NO_INTERNET,
    OTHER

}