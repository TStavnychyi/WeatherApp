package com.tstv.weatherapp.internal

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tstv.weatherapp.R
import com.tstv.weatherapp.data.network.response.vo.ErrorStatus
import com.tstv.weatherapp.data.network.response.vo.ErrorStatus.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

fun getWeatherIconFromStatus(id: String): Int{
    return if(id.startsWith("2")){
        R.drawable.ic_storm
    }else if(id.startsWith("3") || id.startsWith("5")){
        R.drawable.ic_rainy
    }else if (id.startsWith("6")){
        R.drawable.ic_snow
    }else if(id.startsWith("7")){
        R.drawable.ic_atmosphere
    }else if(id == "800"){
        R.drawable.ic_sun
    }else if(id.startsWith("80")){
        R.drawable.ic_clouds
    } else{
        R.drawable.ic_sun
    }
}

fun toOffsetDateTime(millis: Long?): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(millis!!), ZoneId.systemDefault())
}

fun formatHour(time: String): String{
    var result = time
        if(time.length == 1){
            result = "0$time"
        }
    return result
}

fun formatMinutes(minutes: String): String{
    var result = minutes
    if(minutes.length == 1){
        result = "0$minutes"
    }
    return result
}

fun getErrorStatusFromErrorCode(code: Int): ErrorStatus{
    return when(code){
        401 -> INVALID_API_KEY
        404 -> CITY_NOT_FOUND
        429 -> API_KEY_BLOCKED
        500 -> INTERNAL_SERVER_ERROR
        else -> OTHER
    }
}