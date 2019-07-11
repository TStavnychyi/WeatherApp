package com.tstv.weatherapp.di.modules

import com.tstv.weatherapp.ui.search_city.SearchFragment
import com.tstv.weatherapp.ui.weather_detailed.DetailedWeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeDetailedWeatherFragment(): DetailedWeatherFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}