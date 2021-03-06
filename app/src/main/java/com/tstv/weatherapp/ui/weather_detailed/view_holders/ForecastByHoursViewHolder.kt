package com.tstv.weatherapp.ui.weather_detailed.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tstv.weatherapp.R
import com.tstv.weatherapp.data.network.response.vo.DayHourly
import com.tstv.weatherapp.internal.formatHour
import com.tstv.weatherapp.internal.getWeatherIconFromStatus
import com.tstv.weatherapp.internal.toOffsetDateTime
import com.tstv.weatherapp.ui.base.BaseViewHolder

class ForecastByHoursViewHolder (
    private val view: View,
    private val isMetricUnit: Boolean
): BaseViewHolder<DayHourly>(view) {

    companion object {
        fun create(parent: ViewGroup, isMetricUnit: Boolean): ForecastByHoursViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_detailed_weather_by_hour, parent, false)

            countItemWidth(parent, view)

            return ForecastByHoursViewHolder(view, isMetricUnit)
        }

        private fun countItemWidth(parent: ViewGroup, view: View): View{
            val itemsCount = 6
            val itemWidth = parent.width / itemsCount

            val layoutParams = view.layoutParams
            layoutParams.width = itemWidth
            view.layoutParams = layoutParams
            return view
        }
    }

    private val tvHour = view.findViewById<TextView>(R.id.tv_hour)
    private val tvWeatherTemperature = view.findViewById<TextView>(R.id.tv_weather_temperature)
    private val ivWeatherIcon = view.findViewById<ImageView>(R.id.iv_weather_icon)

    override fun bind(item: DayHourly, adapterItemPosition: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        tvWeatherTemperature.text = "${item.main.temp.toInt()}$unitAbbreviation"
        updateCurrentWeatherIcon(item.weather[0]!!.id)
        updateHour(item.dt)
    }

    private fun updateCurrentWeatherIcon(id: Int?){
        Glide.with(view)
            .load(getWeatherIconFromStatus(id!!.toString()))
            .into(ivWeatherIcon)
    }

    private fun updateHour(date: Long?){
        val parsedDate = toOffsetDateTime(date)
        tvHour.text = "${formatHour(parsedDate.hour.toString())}:00"
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if(isMetricUnit) metric else imperial
    }

}