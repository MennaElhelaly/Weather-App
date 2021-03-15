package com.forecast.weather.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.forecast.weather.R
import com.forecast.weather.dataLayer.entity.Weather
import com.forecast.weather.ui.home.loadImage
import kotlinx.android.synthetic.main.row_city.view.*


class FavoriteAdapter(var countries: ArrayList<Weather>,var favoriteCitesViewModel: FavoriteCitesViewModel ) :
        RecyclerView.Adapter<FavoriteAdapter.FavoViewHolder>() {

    fun updateCountries(newCountry: List<Weather>) {
        countries.clear()
        countries.addAll(newCountry)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = FavoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_country, parent, false)
    )
    override fun getItemCount() = countries.size
    override fun onBindViewHolder(holder: FavoViewHolder, position: Int) {
        holder.bind(countries[position])
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, " ${countries[position].timezone}", Toast.LENGTH_SHORT).show()
            favoriteCitesViewModel.onClick(countries[position])
        }
    }
    fun deleteItem(pos: Int) {
        favoriteCitesViewModel.deleteCountry(countries[pos])
        countries.removeAt(pos)
        notifyItemRemoved(pos)
    }
    class FavoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.icon
        private val dt = view.txtCity
        private val description = view.txtDiscription
        private val temp = view.txtTemp
        fun bind(country: Weather) {
            dt.text = country.timezone
            description.text = country.current.weather[0].description
            temp.text=country.current.temp.toInt().toString()+"°"
            when (country.current.weather[0].icon) {
                "01n" -> {
                    imageView.setImageResource(R.drawable.ic_moon8)
                }
                "01d" -> {
                    imageView.setImageResource(R.drawable.ic_sun)
                }
                else -> {
                    loadImage(imageView,country.current.weather[0].icon)
                }
            }

        }
    }

}