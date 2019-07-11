package com.tstv.weatherapp.ui.search_city


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
            }
            initCityAutoCompleteTextView()
        })
        viewModel.getRecentQueries()
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

        et_auto_complete.onItemClickListener = (AdapterView.OnItemClickListener { parent, view, position, id ->
            hideKeyboard()
            openDetailedWeatherFragment(recentQueries.elementAt(position))
        })

        et_auto_complete.setOnKeyListener { _, keyCode, event ->
            if((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                hideKeyboard()
                with(et_auto_complete.text.toString()){
                    if(viewModel.validateQuery(this)) {
                        viewModel.addRecentQuery(this)
                        openDetailedWeatherFragment(this)
                    }else{
                        showToast(getString(R.string.wrong_city_name_error_text))
                    }
                }
                true
            }
            false
        }

        iv_autocomplete_clear.setOnClickListener {
            et_auto_complete.text.clear() }
    }

    private fun showToast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    private fun openDetailedWeatherFragment(city: String){
        val action = SearchFragmentDirections.actionSearchFragmentToDetailedWeatherFragment(city.trim())
        findNavController().navigate(action)
    }

    private fun openKeyboard(){
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private fun hideKeyboard(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
