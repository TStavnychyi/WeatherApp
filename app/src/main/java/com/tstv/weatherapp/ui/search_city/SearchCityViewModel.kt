package com.tstv.weatherapp.ui.search_city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tstv.weatherapp.data.network.response.vo.City
import com.tstv.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchCityViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    fun addRecentQuery(query: String){
        val city = City(name = query.trim().toLowerCase().capitalize())
        viewModelScope.launch {
            if(recentQueries.value?.size == 10){
                val queryToRemove = recentQueries.value!!.elementAt(0)
                removeRecentQuery(queryToRemove)
            }
            repository.addRecentQuery(city)
        }
    }

    fun removeRecentQuery(query: String){
        viewModelScope.launch {
            repository.removeRecentQuery(query.toLowerCase().capitalize())
        }
    }

    private val _recentQueries = MutableLiveData<Set<String>>()
    val recentQueries: MutableLiveData<Set<String>>
        get() = _recentQueries

    fun getRecentQueries(){
        viewModelScope.launch {
            val recentQueriesFromDb = repository.getRecentQueries()
            val queries = recentQueriesFromDb.map { it.name }.toSet()
            _recentQueries.postValue(queries)
        }
    }

    fun validateQuery(query: String): Boolean{
        val regex = Regex("^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*\$")
        return query.matches(regex)
    }

}