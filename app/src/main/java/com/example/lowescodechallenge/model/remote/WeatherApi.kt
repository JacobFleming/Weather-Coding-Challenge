package com.example.lowescodechallenge.model.remote

import com.example.lowescodechallenge.BuildConfig
import com.example.lowescodechallenge.model.CurrentWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(BuildConfig.END_POINT)
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = BuildConfig.KEY
    ): CurrentWeather

    companion object {
        fun getApi(): WeatherApi {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }
}