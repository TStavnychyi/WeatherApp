package com.tstv.weatherapp.data.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import com.tstv.weatherapp.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(
    private val appContext: Context
): Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
       if(!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}