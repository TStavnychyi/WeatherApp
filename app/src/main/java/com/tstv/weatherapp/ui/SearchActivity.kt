package com.tstv.weatherapp.ui

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import com.tstv.weatherapp.R
import com.tstv.weatherapp.data.provider.WeatherSearchSuggestionProvider

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        handleIntent(intent)
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        handleIntent(intent!!)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, WeatherSearchSuggestionProvider.AUTHORITY, WeatherSearchSuggestionProvider.MODE)
                    .saveRecentQuery(query, null)
            }
        }
    }
}
