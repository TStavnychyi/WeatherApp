package com.tstv.weatherapp.ui.settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.tstv.weatherapp.R
import com.tstv.weatherapp.ui.search_city.RecentQueriesAutoCompleteAdapter
import kotlinx.android.synthetic.main.activity_search_city.*

class SearchCityActivity : AppCompatActivity() {

    val recentQueries: MutableList<String> = mutableListOf("Lviv", "Warsaw")

    val adapterInputRecentQueries : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_city)

//        initSearchView()
        initCityAutoCompleteTextView()
    }

//    private fun initSearchView(){
//        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        search_view.apply {
//            val componentName = ComponentName(context, SearchActivity::class.java)
//            setSearchableInfo(searchManager.getSearchableInfo((componentName)))
//        }
//    }



    private fun initCityAutoCompleteTextView(){
        val adapter = RecentQueriesAutoCompleteAdapter(adapterInputRecentQueries, R.layout.adapter_item_autocomplete, this@SearchCityActivity)

        et_auto_complete.setAdapter(adapter)
        et_auto_complete.threshold = 1
        et_auto_complete.addTextChangedListener { object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length >= 3){
                    for(city in recentQueries){
                        if(city.startsWith(s.toString().toLowerCase())){
                            adapterInputRecentQueries.add(city)
                        }
                    }
                    adapter.updateDataList(adapterInputRecentQueries)
                }
            }

        }
        }

        et_auto_complete.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                recentQueries.add(parent!!.getItemAtPosition(position).toString())

            }

        })
    }


}
