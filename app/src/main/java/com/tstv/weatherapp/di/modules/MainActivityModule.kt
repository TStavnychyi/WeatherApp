package com.tstv.weatherapp.di.modules

import com.tstv.weatherapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun contributeMainActivity(): MainActivity
}