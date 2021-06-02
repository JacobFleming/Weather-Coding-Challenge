package com.example.lowescodechallenge.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lowescodechallenge.MainActivity
import com.example.lowescodechallenge.databinding.CityForecasteFragmentBinding
import com.example.lowescodechallenge.model.CurrentWeather
import com.example.lowescodechallenge.model.Repository
import com.example.lowescodechallenge.model.RepositoryImpl
import com.example.lowescodechallenge.view.adapter.CityForecastAdapter
import com.example.lowescodechallenge.viewmodel.WeatherViewModel

class CityForecast: Fragment() {

    interface IDetailFragment{fun displayDetail(position: Int) }
    companion object{
        private val KEY_CITY_NAME: String = "CityForecast_KEY_CITY_NAME"

        fun newInstance(cityName: String) = CityForecast().apply {
            arguments = Bundle().apply {
                putString(KEY_CITY_NAME, cityName)
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

//    private val viewmodel: WeatherViewModel by activityViewModels{requireActivity()},{
//        viewmodelProvider.create(WeatherViewModel)
//    }

    private lateinit var binding: CityForecasteFragmentBinding

    private val adapter: CityForecastAdapter by lazy {
        CityForecastAdapter(callback = ::openDetailFragmentCallback)
    }

    private lateinit var listener: IDetailFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is IDetailFragment -> listener = context
            else -> throw ExceptionInInitializerError("Incorrect Host Activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CityForecasteFragmentBinding.inflate(inflater)
        setupUI()
        viewModel.livedata.observe(viewLifecycleOwner){
            updateView(it)
        }
        configureViewModel()
        return binding.root
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun configureViewModel() {
        arguments?.let {
            it.getString(KEY_CITY_NAME)?.let { cityName->
                viewModel.getCityForecastFlow(cityName)
            }
        }
    }

    private fun updateView(data: CurrentWeather?) {
        if (data != null)
            updateAdapter(data)
    }

    private fun updateAdapter(data: CurrentWeather) {
        adapter.updateDataSet(data.list)
    }

    private fun configureToolbar(){

        val activity: MainActivity = activity as MainActivity
activity.toolbar.visibility=Toolbar.VISIBLE
        arguments?.let {
            it.getString(KEY_CITY_NAME)?.let { cityName->
                activity.toolbar.setTitle(cityName)
            }
        }
    }

    private fun openDetailFragmentCallback(position: Int){
        listener.displayDetail(position)
    }
    override fun onResume() {
        super.onResume()
        configureToolbar()
    }
}