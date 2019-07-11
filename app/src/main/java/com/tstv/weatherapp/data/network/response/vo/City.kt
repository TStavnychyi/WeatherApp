package com.tstv.weatherapp.data.network.response.vo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cities_recent_queries")
data class City(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String
){
}