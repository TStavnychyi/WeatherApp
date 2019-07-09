package com.tstv.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tstv.weatherapp.data.db.entity.CURRENT_WEATHER_ID
import com.tstv.weatherapp.data.db.entity.WeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntry: WeatherEntry)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getCurrentWeather(): WeatherEntry

}