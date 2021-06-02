package com.example.lowescodechallenge.model

import com.example.lowescodechallenge.model.remote.WeatherApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf

class RepositoryImpl: Repository {

    private val network = WeatherApi.getApi()

    override suspend fun getWeather(cityName: String): Flow<CurrentWeather> {
//        return callbackFlow<CurrentWeather> {
//            send(
//                network.getWeather(cityName)
//            )
//        }
        return flowOf(network.getWeather(cityName))
    }

    override suspend fun getWeatherC(cityName: String): CurrentWeather {
        return network.getWeather(cityName)
    }
}