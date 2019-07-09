package com.tstv.weatherapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tstv.weatherapp.di.keys.ViewModelKey
import com.tstv.weatherapp.ui.DetailedWeatherViewModel
import com.tstv.weatherapp.view_model.WeatherViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailedWeatherViewModel::class)
    abstract fun bindDetailedWeatherViewModel(detailedWeatherViewModel: DetailedWeatherViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: WeatherViewModelFactory): ViewModelProvider.Factory

}