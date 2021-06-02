package com.example.lowescodechallenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lowescodechallenge.databinding.ForecastItemLayoutBinding
import com.example.lowescodechallenge.model.WeatherData
import com.example.lowescodechallenge.util.util.Companion.kelvinToFahrenheit

class CityForecastAdapter(private var dataSet: List<WeatherData>? = null,
    private val callback: (Int) -> Unit): RecyclerView.Adapter<CityForecastAdapter.CityForecastViewHolder>() {

    class CityForecastViewHolder(private val binding: ForecastItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
            fun onBind(forecastItem: WeatherData, callback: (Int)-> Unit){
                binding.root.setOnClickListener { callback(adapterPosition) }

                binding.weatherCondition.text = forecastItem.weather[0].main
                binding.weatherTemp.text = "Temp:  "+ Double.kelvinToFahrenheit(forecastItem.main.temp).toString()
            }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CityForecastViewHolder(ForecastItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
        parent, false))

    override fun onBindViewHolder(holder: CityForecastViewHolder, position: Int) {
        dataSet?.let{
            holder.onBind(it[position], callback)
        }
    }

    override fun getItemCount() = dataSet?.size ?: 0

    fun updateDataSet(dataSet: List<WeatherData>?){
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


}


