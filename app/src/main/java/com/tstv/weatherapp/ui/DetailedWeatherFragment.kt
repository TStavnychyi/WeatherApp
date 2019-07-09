package com.tstv.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tstv.weatherapp.R
import com.tstv.weatherapp.di.Injectable
import com.tstv.weatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_weather_detail_layout.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailedWeatherFragment : ScopedFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: DetailedWeatherViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather_detail_layout, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this@DetailedWeatherFragment, viewModelFactory).get(DetailedWeatherViewModel::class.java)

        bindUI()

    }

    private fun bindUI() = launch{
        viewModel.loadWeather("London")

        val currentWeather = viewModel.weather
        currentWeather.observe(this@DetailedWeatherFragment, Observer {
            if(it == null) return@Observer

            updateTemperatures(it.current.tempC, it.current.feelslikeC)

        })

        Log.e("Fragment", "Ready ")

    }


    @SuppressLint("SetTextI18n")
    private fun updateTemperatures(currentTemp: Double, feelsLike: Double){
//        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        val unitAbbreviation = "°C"
        tv_current_temperature.text = "$currentTemp$unitAbbreviation"
        tv_feels_like.text = "Feels like - $feelsLike$unitAbbreviation"
    }
}