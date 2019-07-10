package com.tstv.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tstv.weatherapp.data.network.response.vo.City

@Dao
interface CitiesRecentQueriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Query("select * from cities_recent_queries")
    fun getAllRecentQueries(): List<City>

}