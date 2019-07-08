package com.tstv.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tstv.weatherapp.ui.DetailedWeatherFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragment = DetailedWeatherFragment()
        val fragmentTransaction =  supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
