package com.tstv.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tstv.weatherapp.data.network.response.vo.City
import com.tstv.weatherapp.internal.LocalDateConverter

@Database(entities = [City::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class WeatherDatabase : RoomDatabase(){

    abstract fun currentWeatherDao(): CitiesRecentQueriesDao

    companion object {
        @Volatile private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, "weather.db")
            .build()
    }
}