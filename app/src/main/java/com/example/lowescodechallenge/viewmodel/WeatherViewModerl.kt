package com.example.lowescodechallenge.viewmodel

import androidx.lifecycle.*
import com.example.lowescodechallenge.model.CurrentWeather
import com.example.lowescodechallenge.model.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class WeatherViewModel(private val repository: Repository) : ViewModel() {

    class WeatherViewModelProvider(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WeatherViewModel(repository) as T
        }
    }



    private val mutableLiveData = MutableLiveData<CurrentWeather>()

    val livedata: LiveData<CurrentWeather> = mutableLiveData

    fun getCityForecastFlow(cityName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val flow = repository.getWeather(cityName)

            withContext(Dispatchers.Main) {
                flow.collect{
                    mutableLiveData.value = it
                }
            }
        }
    }
}