package com.tstv.weatherapp.ui.search_city

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filterable
import com.tstv.weatherapp.R

class RecentQueriesAutoCompleteAdapter(
    private val dataList: MutableList<String>,
    private val resource: Int,
    private val cntx: Context
    ) : ArrayAdapter<String>(cntx, resource), Filterable  {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(cntx)

        return inflater.inflate(R.layout.adapter_item_autocomplete, parent, false)
    }

    fun updateDataList(list: List<String>){
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getCount() = dataList.size

    override fun getItem(position: Int) = dataList[position]

}