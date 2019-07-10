package com.tstv.weatherapp.internal

import com.tstv.weatherapp.R
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

fun formatTime(time: String): String{
    var result = time
        if(time.length == 1){
            result = "0$time"
        }
    result = "$result:00"
    return result
}
