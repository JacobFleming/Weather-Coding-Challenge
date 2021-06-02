package com.example.lowescodechallenge

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.lowescodechallenge.databinding.ActivityMainBinding
import com.example.lowescodechallenge.view.CityForecast
import com.example.lowescodechallenge.view.CityForecastDetail
import com.example.lowescodechallenge.view.CitySearchFragment

class MainActivity : AppCompatActivity(), CitySearchFragment.ISearchCity,
    CityForecast.IDetailFragment {

    private lateinit var binding: ActivityMainBinding
    public lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        inflateCitySearchFragment()
    }

    private fun openDetailFragment(position: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CityForecastDetail.newInstance(position))
            .addToBackStack(null)
            .commit()
    }

    private fun openWeatherFragment(cityName: String) {


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CityForecast.newInstance(cityName))
            .addToBackStack(null)

            .commit()
    }

    private fun inflateCitySearchFragment() {


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CitySearchFragment()).addToBackStack(null)

            .commit()
    }

    override fun searchData(cityName: String) {
        openWeatherFragment(cityName)
    }

    override fun displayDetail(position: Int) {
        openDetailFragment(position)
    }

}