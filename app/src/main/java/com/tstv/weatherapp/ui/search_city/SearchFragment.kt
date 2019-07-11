package com.tstv.weatherapp.ui.search_city


import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tstv.weatherapp.R
import com.tstv.weatherapp.di.Injectable
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchCityViewModel

    private var recentQueries = mutableSetOf<String>()

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this@SearchFragment, viewModelFactory).get(SearchCityViewModel::class.java)

        bindUI()
    }

    private fun bindUI(){
        viewModel.recentQueries.observe(this, Observer {
            if (!it.isNullOrEmpty()){
                recentQueries = it.toMutableSet()
                initCityAutoCompleteTextView()
            }
        })
        viewModel.getRecentQueris()
        openKeyboard()
    }

    private fun initCityAutoCompleteTextView(){
        adapter = ArrayAdapter(context!!, android.R.layout.simple_dropdown_item_1line, recentQueries.toList())
        et_auto_complete.setAdapter(adapter)
        et_auto_complete.setOnClickListener {
            val widrh= et_auto_complete.dropDownWidth
            if(recentQueries.isNotEmpty())
                et_auto_complete.showDropDown()
        }

        et_auto_complete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id -> openDetailedWeatherFragment(recentQueries.elementAt(position)) }

        et_auto_complete.setOnKeyListener { _, keyCode, event ->
            if((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                with(et_auto_complete.text.toString()){
                    viewModel.addRecentQuery(this)
                    openDetailedWeatherFragment(this)
                }
                true
            }
            false
        }

        iv_autocomplete_clear.setOnClickListener { et_auto_complete.text.clear() }
    }

    private fun openDetailedWeatherFragment(city: String){
        val action = SearchFragmentDirections.actionSearchFragmentToDetailedWeatherFragment(city.trim())
        findNavController().navigate(action)
    }

    private fun openKeyboard(){
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}
