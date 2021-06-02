package com.example.lowescodechallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lowescodechallenge.databinding.CityForecastDetailFragmentBinding
import com.example.lowescodechallenge.model.Repository
import com.example.lowescodechallenge.model.RepositoryImpl
import com.example.lowescodechallenge.model.WeatherData
import com.example.lowescodechallenge.util.util.Companion.kelvinToFahrenheit
import com.example.lowescodechallenge.viewmodel.WeatherViewModel

class CityForecastDetail: Fragment() {

    companion object{
        const val KEY_CITY_LIST_POSITION = "CityForecastDetails_KEY_CITY_LIST_POSITION"

        fun newInstance(position: Int) =
            CityForecastDetail().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CITY_LIST_POSITION, position)
                }
            }
    }

    //@Inject
    val repository: Repository by lazy { RepositoryImpl() }
    val viewmodelProvider: WeatherViewModel.WeatherViewModelProvider by lazy {
        WeatherViewModel.WeatherViewModelProvider(repository)
    }

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(requireActivity(), viewmodelProvider)[WeatherViewModel::class.java]
    }

    private lateinit var binding: CityForecastDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CityForecastDetailFragmentBinding.inflate(inflater)

            viewModel.livedata.observe(viewLifecycleOwner){dataSet->
                arguments?.let { bundle->

                    updateUI(dataSet.list[bundle.getInt(KEY_CITY_LIST_POSITION)])
                }
            }

        return binding.root
    }

    private fun updateUI(weatherData: WeatherData) {
        binding.tvFeelsLikeNumberDetail.text = Double.kelvinToFahrenheit(weatherData.main.feels_like).toString()
        binding.tvTempNumberDetail.text = Double.kelvinToFahrenheit(weatherData.main.temp).toString()
        binding.tvWeatherStatusDetail.text = weatherData.weather[0].main
        binding.tvWeatherStatusDescriptionDetail.text = weatherData.weather[0].description
    }

}