package com.jnda.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jnda.common.DateExtension
import com.jnda.common.KotlinUtil
import com.jnda.common.loadWeatherIcon
import com.jnda.home.databinding.ItemWeatherBinding
import com.jnda.home.domain.dto.WeatherItemDTO

class WeatherAdapter(
    private val items: ArrayList<WeatherItemDTO>
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = items[position]
        holder.update(item)
    }

    fun replaceItems(items: ArrayList<WeatherItemDTO>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class WeatherViewHolder(val binding: ItemWeatherBinding) : ViewHolder(binding.root) {

        fun update(item: WeatherItemDTO) {
            binding.ivWeatherIcon.loadWeatherIcon(item.icon)
            binding.tvTemp.text = item.temp
            binding.tvDate.text = DateExtension.getFormattedDate(item.dateTime.toLong())
            binding.tvDescription.text = KotlinUtil.makeTitleCase(item.description)
        }
    }
}