package com.tstv.weatherapp.ui.weather_detailed.view_holders

import android.annotation.SuppressLint
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
import org.threeten.bp.format.TextStyle
import java.util.*

class ForecastByDaysViewHolder(
    private val view: View,
    private val isMetricUnit: Boolean
): BaseViewHolder<Day>(view), View.OnClickListener {

    companion object{
        fun create(parent: ViewGroup, isMetricUnit: Boolean): ForecastByDaysViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_detailed_weather_on_next_days, parent, false)
            return ForecastByDaysViewHolder(view, isMetricUnit)
        }
    }

    private val tvDay = view.findViewById<TextView>(R.id.tv_day)
    private val tvMinTemperature = view.findViewById<TextView>(R.id.tv_min_temperature)
    private val tvMaxTemperature = view.findViewById<TextView>(R.id.tv_max_temperature)
    private val ivWeatherIcon = view.findViewById<ImageView>(R.id.iv_weather_icon)

    private lateinit var weatherObj: Day

    @SuppressLint("SetTextI18n")
    override fun bind(item: Day, adapterItemPosition: Int) {
        weatherObj = item
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        tvMinTemperature.text = " / ${item.temp.min.toInt()}$unitAbbreviation"
        tvMaxTemperature.text = "${item.temp.max.toInt()}$unitAbbreviation"
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

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if(isMetricUnit) metric else imperial
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}