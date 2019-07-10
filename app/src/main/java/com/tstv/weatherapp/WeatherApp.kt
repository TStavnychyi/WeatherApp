package com.tstv.weatherapp

import android.app.Activity
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tstv.weatherapp.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class WeatherApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}