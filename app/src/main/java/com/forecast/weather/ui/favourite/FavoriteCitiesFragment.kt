package com.forecast.weather.ui.favourite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.forecast.weather.R
import com.forecast.weather.dataLayer.entity.Weather
import com.forecast.weather.databinding.FragmentFavoriteBinding
import com.forecast.weather.ui.favorite_datails.FavouriteDetails
import com.forecast.weather.ui.map.Maps

class FavoriteCitiesFragment : Fragment() {

    private lateinit var favoriteCitesViewModel: FavoriteCitesViewModel
    lateinit var countryAdapter : FavoriteAdapter
    private lateinit var binding: FragmentFavoriteBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        favoriteCitesViewModel = ViewModelProvider(this).get(FavoriteCitesViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        countryAdapter = FavoriteAdapter(arrayListOf(),favoriteCitesViewModel)
        binding.btnMap.setOnClickListener {
            activity?.let {
                val intent = Intent(it, Maps::class.java)
                it.startActivity(intent)
            }
        }
        initUI()
        getCurrent()

        favoriteCitesViewModel.getnavigation().observe(viewLifecycleOwner, {
            if (it!=null) {
                val intent = Intent(this.context, FavouriteDetails::class.java)
                intent.putExtra("Lat", it.lat.toString())
                intent.putExtra("Long", it.lon.toString())
                Log.i("hh","intentmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm ${it.lat} /// ${it.lon}")
                startActivity(intent)
            }
        })

        return binding.root
    }
    private fun initUI() {
        binding.recyclerCountries.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryAdapter
        }
        val itemTouchHelper =ItemTouchHelper(SwipeToDelete(countryAdapter,this.requireContext()))
        itemTouchHelper.attachToRecyclerView(binding.recyclerCountries)

    }
    fun getCurrent(){
        favoriteCitesViewModel.allCountries().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                updateCountryList(it)
            }
        })
    }
    private fun updateCountryList(it: List<Weather>) {
        countryAdapter.updateCountries(it)
    }


}