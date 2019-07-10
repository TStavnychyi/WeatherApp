package com.tstv.weatherapp.ui.weather_detailed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tstv.weatherapp.R
import com.tstv.weatherapp.data.network.response.vo.Day
import com.tstv.weatherapp.data.network.response.vo.DayHourly
import com.tstv.weatherapp.di.Injectable
import com.tstv.weatherapp.internal.getWeatherIconFromStatus
import com.tstv.weatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.additional_weather_data_block.*
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
        viewModel.loadWeather("Gliwice")
        viewModel.loadWeatherByHour("Gliwice")

        val currentWeather = viewModel.weather
        val weatherByHour = viewModel.weatherByHour
        currentWeather.observe(this@DetailedWeatherFragment, Observer {
            if(it == null) return@Observer

            val weather = it.list[0]
            with(weather.temp){
                updateTemperatures(day, min, max)
            }
            updateCurrentWeatherIcon(weather.weather[0]?.id)
            updateLocation(it.city.name)
            updateAdditionalWeatherData(weather.humidity, weather.speed, weather.pressure)
            initForecastByDaysRecyclerView(it.list.drop(1))
        })
        weatherByHour.observe(this@DetailedWeatherFragment, Observer {
            if(it == null) return@Observer
            initForecastByHoursRecyclerView(it.list)

            hideLoadingViewAndShowContentViews()
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateTemperatures(currentTemp: Double, minTemp: Double, maxTemp: Double){
//        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        val unitAbbreviation = "°C"
        tv_current_temperature.text = "${currentTemp.toInt()}$unitAbbreviation"
        tv_min_max.text = "${maxTemp.toInt()}$unitAbbreviation/${minTemp.toInt()}$unitAbbreviation"
    }

    private fun updateLocation(location: String){
        tv_city.text = location.capitalize()
    }

    private fun initForecastByHoursRecyclerView(items: List<DayHourly>){
        val forecastAdapter = DetailedWeatherForecastAdapter(items)

        rv_temperature_by_hours.apply {
            layoutManager = LinearLayoutManager(this@DetailedWeatherFragment.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastAdapter
            setHasFixedSize(true)
        }
    }

    private fun updateAdditionalWeatherData(humidity: Double?, windSpeed: Double?, pressure: Double?){
        tv_humidity_value.text = humidity.toString()
        tv_wind_speed_value.text = windSpeed.toString()
        tv_pressure_value.text = pressure.toString()
    }

    private fun initForecastByDaysRecyclerView(items: List<Day>) {
        val forecastAdapter = DetailedWeatherForecastAdapter(items)

        rv_temperature_on_next_days.apply {
            layoutManager = LinearLayoutManager(this@DetailedWeatherFragment.context)
            adapter = forecastAdapter
            setHasFixedSize(true)
        }
    }

    private fun updateCurrentWeatherIcon(id: Int?){
        Glide.with(this@DetailedWeatherFragment)
            .load(getWeatherIconFromStatus(id!!.toString()))
            .into(iv_weather_icon)
    }

    private fun hideLoadingViewAndShowContentViews(){
        weather_group_loading_bar.visibility = View.GONE

        ll_additional_weather_data.visibility = View.VISIBLE
        weather_group_content.visibility = View.VISIBLE
        ll_location_view.visibility = View.VISIBLE

    }
}