package com.tstv.weatherapp.data.provider

import android.content.SearchRecentSuggestionsProvider

class WeatherSearchSuggestionProvider : SearchRecentSuggestionsProvider() {

    companion object{
        const val AUTHORITY = "com.tstv.WeatherSearchSuggestionProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }

    init {
        setupSuggestions(AUTHORITY, MODE)
    }
}