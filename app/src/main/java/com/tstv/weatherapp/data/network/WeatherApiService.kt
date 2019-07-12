package com.tstv.weatherapp.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tstv.weatherapp.data.network.response.ForecastHourlyResponse
import com.tstv.weatherapp.data.network.response.ForecastResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "433a283bd3d004656b0f52e2c566cfc9"

interface WeatherApiService {

    @GET("forecast/daily")
    fun getForecast(
        @Query("q") location: String,
        @Query("units") units: String = "metric"
    ): Deferred<ForecastResponse>

    @GET("forecast")
    fun getForecastByHour(
        @Query("q") location: String,
        @Query("units") units: String = "metric",
        @Query("cnt") resultLimit: Int = 6
    ): Deferred<ForecastHourlyResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("APPID", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}