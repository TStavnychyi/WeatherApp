package com.tstv.weatherapp.ui


import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tstv.weatherapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var suggestions: SearchRecentSuggestions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchView()
    }

    private fun initSearchView(){
        val searchManager: SearchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        citySearchVie1w.apply {
            val componentName = ComponentName(activity!!, SearchActivity::class.java)
            setSearchableInfo(searchManager.getSearchableInfo((componentName)))
        }
    }


//    private fun initSearchView(){
//        val searchManager: SearchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        citySearchView.apply {
//            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
//            setIconifiedByDefault(false)
//        }
//    }

//    private fun initSearchView(){
//        val searchManager: SearchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        suggestions = SearchRecentSuggestions(context, WeatherSearchSuggestionProvider.AUTHORITY, WeatherSearchSuggestionProvider.MODE)
//
//        with(citySearchView){
//            citySearchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
//            setIconifiedByDefault(false)
//            activity?.onSearchRequested()
//            isQueryRefinementEnabled = true
//            requestFocus(1)
//
//            setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//                override fun onQueryTextChange(newText: String?): Boolean {
//
//                    return true
//
//                }
//
//                override fun onQueryTextSubmit(query: String?): Boolean {
//
//                    return true
//                }
//            })
//
//            setOnSuggestionListener(object : SearchView.OnSuggestionListener{
//                override fun onSuggestionSelect(position: Int): Boolean {
//
//                    return true
//                }
//
//                override fun onSuggestionClick(position: Int): Boolean {
//                    val selectedView = citySearchView.suggestionsAdapter
//                    val cursor = selectedView.getItem(position) as Cursor
//                    val index = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1)
//                    citySearchView.setQuery(cursor.getString(index), true)
//                    return true
//                }
//            })
//
//
//        }
//    }


}
