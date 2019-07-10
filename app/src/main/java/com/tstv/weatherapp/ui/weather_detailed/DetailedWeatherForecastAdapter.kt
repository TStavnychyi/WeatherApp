package com.tstv.weatherapp.ui.weather_detailed

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tstv.weatherapp.data.network.response.vo.Day
import com.tstv.weatherapp.data.network.response.vo.DayHourly
import com.tstv.weatherapp.data.network.response.vo.IDay
import com.tstv.weatherapp.ui.base.BaseViewHolder
import com.tstv.weatherapp.ui.weather_detailed.view_holders.ForecastByDaysViewHolder
import com.tstv.weatherapp.ui.weather_detailed.view_holders.ForecastByHoursViewHolder

class DetailedWeatherForecastAdapter(
    private val dataList: List<IDay>
): RecyclerView.Adapter<BaseViewHolder<IDay>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<IDay> {
        return when {
            dataList.checkItemsAre<Day>() -> ForecastByDaysViewHolder.create(parent)
            dataList.checkItemsAre<DayHourly>() -> ForecastByHoursViewHolder.create(parent)
            else -> ForecastByDaysViewHolder.create(parent)
        } as BaseViewHolder<IDay>
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder<IDay>, position: Int) {
        val item = dataList[position]
        holder.bind(item, position)
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> List<*>.checkItemsAre() = all { it is T }
}