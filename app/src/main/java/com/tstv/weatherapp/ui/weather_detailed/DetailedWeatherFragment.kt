package com.tstv.weatherapp.ui.weather_detailed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tstv.weatherapp.R
import com.tstv.weatherapp.data.network.response.vo.Day
import com.tstv.weatherapp.data.network.response.vo.DayHourly
import com.tstv.weatherapp.data.network.response.vo.ErrorStatus
import com.tstv.weatherapp.di.Injectable
import com.tstv.weatherapp.internal.UnitSystem
import com.tstv.weatherapp.internal.formatHour
import com.tstv.weatherapp.internal.formatMinutes
import com.tstv.weatherapp.internal.getWeatherIconFromStatus
import com.tstv.weatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.additional_weather_data_block.*
import kotlinx.android.synthetic.main.fragment_weather_detail_layout.*
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.TextStyle
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class DetailedWeatherFragment : ScopedFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailedWeatherViewModel

    private var lastUnitSystem: UnitSystem? = null

    @Volatile private var isLoadingFinished = false

    private var cityName: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather_detail_layout, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this@DetailedWeatherFragment, viewModelFactory).get(DetailedWeatherViewModel::class.java)

        val argsCityName = arguments?.let { DetailedWeatherFragmentArgs.fromBundle(it) }
        cityName = argsCityName?.argCityName!!

        bindUI(cityName)

    }

    private fun bindUI(cityName: String) = launch {
        if(lastUnitSystem == null || lastUnitSystem != viewModel.getUnitSystem()) {
            viewModel.loadWeather(cityName, viewModel.getUnitSystem())
            viewModel.loadWeatherByHour(cityName, viewModel.getUnitSystem())
        }

        initToolbar()

        val currentWeather = viewModel.weather
        val weatherByHour = viewModel.weatherByHour

        currentWeather.observe(this@DetailedWeatherFragment, Observer {
            if(it == null) return@Observer
            if(it.error != null){
                handleError(it.error!!)
                return@Observer
            }

            val weather = it.list[0]
            with(weather.temp){
                updateTemperatures(day, min, max)
            }
            updateCurrentWeatherIcon(weather.weather[0]?.id)
            updateCurrentDate()
            updateLocation(it.city.name)
            updateAdditionalWeatherData(weather.humidity, weather.speed, weather.pressure)
            initForecastByDaysRecyclerView(it.list.drop(1))

            hideLoadingViewAndShowContentViews()
        })
        weatherByHour.observe(this@DetailedWeatherFragment, Observer {
            if(it == null) return@Observer
            if(it.error != null){
                handleError(it.error!!)
                return@Observer
            }
            initForecastByHoursRecyclerView(it.list)
            setLayoutBackgroundColorDependsOnTime()
            hideLoadingViewAndShowContentViews()
        })

        lastUnitSystem = viewModel.getUnitSystem()

        root_view.setOnRefreshListener {
            ll_no_internet_connection_view.visibility = View.GONE

            viewModel.retry(cityName, viewModel.getUnitSystem())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTemperatures(currentTemp: Double, minTemp: Double, maxTemp: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        tv_current_temperature.text = "${currentTemp.toInt()}$unitAbbreviation"
        tv_current_temperature.setTextColor(activity?.getColor(viewModel.getTextColorFromTemperature(currentTemp.toInt(), viewModel.getUnitSystem()))!!)
        tv_min_max.text = "${maxTemp.toInt()}$unitAbbreviation / ${minTemp.toInt()}$unitAbbreviation"
    }

    private fun updateLocation(location: String){
        tv_city.text = location.capitalize()
    }

    private fun initForecastByHoursRecyclerView(items: List<DayHourly>){
        val forecastAdapter = DetailedWeatherForecastAdapter(items, viewModel.isMetricUnit)

        rv_temperature_by_hours.apply {
            layoutManager = LinearLayoutManager(this@DetailedWeatherFragment.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastAdapter
            setHasFixedSize(true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateAdditionalWeatherData(humidity: Double?, windSpeed: Double?, pressure: Double?){
        tv_humidity_value.text = "${humidity?.roundToInt()}%"
        tv_wind_speed_value.text = "${windSpeed?.roundToInt()} kph"
        tv_pressure_value.text = "${pressure?.roundToInt()} hPa"
    }

    private fun initForecastByDaysRecyclerView(items: List<Day>) {
        val forecastAdapter = DetailedWeatherForecastAdapter(items, viewModel.isMetricUnit)

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

    @SuppressLint("SetTextI18n")
    private fun updateCurrentDate(){
        val parsedDate = LocalDateTime.now()

        with(parsedDate){
            val dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH).capitalize()
            val monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).capitalize()
            val time = "${formatHour(hour.toString())}:${formatMinutes(minute.toString())}"
            tv_current_date.text = "$dayName, $dayOfMonth $monthName $time"
        }
    }

    private fun hideLoadingViewAndShowContentViews(){
        if(isLoadingFinished) {
            weather_group_loading_bar.visibility = View.GONE
            ll_no_internet_connection_view.visibility = View.GONE

            if(root_view.isRefreshing)
                root_view.isRefreshing = false
            showContentViews(true)
            animateWeatherIcon()
            animateContentViews()
        }
        isLoadingFinished = true
    }

    private fun setLayoutBackgroundColorDependsOnTime(){
        if(viewModel.getIsDay()){
            root_view.background = context?.getDrawable(R.drawable.fragment_weather_detail_day_background)
        }else{
            root_view.background = context?.getDrawable(R.drawable.fragment_weather_detail_night_background)
        }
    }

    private fun initToolbar(){
        toolbar.inflateMenu(R.menu.detailed_weather_toolbar_menu)
        toolbar.overflowIcon = context?.getDrawable(R.drawable.ic_more_vert_white)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.options_menu -> {
                    openSettingsFragment()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if(viewModel.isMetricUnit) metric else imperial
    }

    private fun openSettingsFragment(){
        val direction = DetailedWeatherFragmentDirections.actionToSettings()
        findNavController().navigate(direction)
    }

    private fun showContentViews(show: Boolean){
        val contentViewsVisibilityId = if(show) View.VISIBLE else View.GONE

        ll_additional_weather_data.visibility = contentViewsVisibilityId
        ll_weather_main_info.visibility = contentViewsVisibilityId
        ll_future_forecast_block.visibility = contentViewsVisibilityId
        ll_location_view.visibility = contentViewsVisibilityId
        iv_weather_icon.visibility = contentViewsVisibilityId
    }

    private fun animateWeatherIcon(){
        val animFadeIn = AnimationUtils.loadAnimation(context!!, R.anim.slide_in_right)

        animFadeIn.reset()
        iv_weather_icon.clearAnimation()
        iv_weather_icon.startAnimation(animFadeIn)
    }

    private fun animateContentViews(){
        val animFadeOut = AnimationUtils.loadAnimation(context!!, R.anim.fade_in)

        animFadeOut.reset()
        ll_additional_weather_data.clearAnimation()
        ll_additional_weather_data.startAnimation(animFadeOut)

        animFadeOut.reset()
        ll_weather_main_info.clearAnimation()
        ll_weather_main_info.startAnimation(animFadeOut)

        animFadeOut.reset()
        ll_future_forecast_block.clearAnimation()
        ll_future_forecast_block.startAnimation(animFadeOut)

        animFadeOut.reset()
        ll_location_view.clearAnimation()
        ll_location_view.startAnimation(animFadeOut)
    }

    private fun handleError(error: ErrorStatus){
        when (error) {
            ErrorStatus.CITY_NOT_FOUND -> {
                viewModel.removeRecentQuery(cityName)
                tv_error_text.text = getString(R.string.wrong_city_name_error_text)
                iv_error_icon.setImageDrawable(activity?.getDrawable(R.drawable.ic_error))
            }
            ErrorStatus.NO_INTERNET -> {
                tv_error_text.text = getString(R.string.no_internet_connection_error_text)
                iv_error_icon.setImageDrawable(activity?.getDrawable(R.drawable.ic_no_internet))
            }
            else -> {
                tv_error_text.text = getString(R.string.other_error_text)
                iv_error_icon.setImageDrawable(activity?.getDrawable(R.drawable.ic_error))
            }
        }
        showContentViews(false)
        weather_group_loading_bar.visibility = View.GONE
        ll_no_internet_connection_view.visibility = View.VISIBLE

        if(root_view.isRefreshing)
            root_view.isRefreshing = false
    }

}