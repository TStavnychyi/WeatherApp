package com.tstv.weatherapp.ui.weather_detailed.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tstv.weatherapp.R
import com.tstv.weatherapp.data.network.response.vo.Day
import com.tstv.weatherapp.internal.getWeatherIconFromStatus
import com.tstv.weatherapp.internal.toOffsetDateTime
import com.tstv.weatherapp.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_weather_detail_layout.*
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.WeekFields
import java.util.*

class ForecastByDaysViewHolder(
    private val view: View
): BaseViewHolder<Day>(view) {

    companion object{
        fun create(parent: ViewGroup): ForecastByDaysViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_detailed_weather_on_next_days, parent, false)
            return ForecastByDaysViewHolder(view)
        }
    }

    private val tvDay = view.findViewById<TextView>(R.id.tv_day)
    private val tvMinTemperature = view.findViewById<TextView>(R.id.tv_min_temperature)
    private val tvMaxTemperature = view.findViewById<TextView>(R.id.tv_max_temperature)
    private val ivWeatherIcon = view.findViewById<ImageView>(R.id.iv_weather_icon)

    override fun bind(item: Day, adapterItemPosition: Int) {
        tvMinTemperature.text = "/${item.temp.min.toInt()}°"
        tvMaxTemperature.text = "${item.temp.max.toInt()}"
        updateCurrentWeatherIcon(item.weather[0]!!.id)
        updateDate(item.dt)
    }

    private fun updateCurrentWeatherIcon(id: Int?){
        Glide.with(view)
            .load(getWeatherIconFromStatus(id!!.toString()))
            .into(ivWeatherIcon)
    }

    private fun updateDate(date: Long?){
        val parsedDate = toOffsetDateTime(date)
        val dayName = parsedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        tvDay.text = dayName.capitalize()
    }
}