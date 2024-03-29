package com.example.lowescodechallenge.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.lowescodechallenge.MainActivity
import com.example.lowescodechallenge.databinding.CitySearchFragmentBinding

class CitySearchFragment : Fragment() {

    interface ISearchCity {
        fun searchData(cityName: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is ISearchCity -> listener = context
            else -> throw ExceptionInInitializerError("Incorrect Host Activity")
        }
    }

    private lateinit var listener: ISearchCity

    private lateinit var binding: CitySearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CitySearchFragmentBinding.inflate(inflater)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.btnLookup.setOnClickListener {
            binding.tilSearch.editText?.let {
                if (it.text.isNotEmpty())
                    listener.searchData(it.text.toString())
                else
                    showToast()
            }
        }
    }

    override fun onResume() {
        super.onResume()
configureToolbar()
    }

    private fun showToast() {
        Toast.makeText(context, "No empty city search", Toast.LENGTH_LONG).show()
    }

    private fun configureToolbar() {

        val activity: MainActivity = activity as MainActivity
        activity.toolbar.visibility = Toolbar.INVISIBLE
    }
}