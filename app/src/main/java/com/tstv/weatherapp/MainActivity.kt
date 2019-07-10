package com.tstv.weatherapp

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tstv.weatherapp.internal.inTransaction
import com.tstv.weatherapp.ui.SearchActivity
import com.tstv.weatherapp.ui.weather_detailed.DetailedWeatherFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        initSearchView()

//        val fragment = DetailedWeatherFragment()
//        supportFragmentManager.inTransaction {
//            add(R.id.fragment_container, fragment)
//            addToBackStack(null)
//        }
    }


    override fun supportFragmentInjector() = dispatchingAndroidInjector

//    private fun initSearchView(){
//        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        citySearchView.apply {
//            val componentName = ComponentName(context, SearchActivity::class.java)
//            setSearchableInfo(searchManager.getSearchableInfo((componentName)))
//        }
//    }

}
