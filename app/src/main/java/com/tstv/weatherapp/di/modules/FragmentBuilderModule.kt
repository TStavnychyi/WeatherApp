package com.tstv.weatherapp.di.modules

import com.tstv.weatherapp.ui.DetailedWeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeDetailedWeatherFragment(): DetailedWeatherFragment
}