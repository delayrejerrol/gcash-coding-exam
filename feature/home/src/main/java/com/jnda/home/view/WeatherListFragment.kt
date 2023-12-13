package com.jnda.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jnda.common.view.BaseFragment
import com.jnda.home.databinding.FragmentWeatherListBinding
import com.jnda.home.view.adapter.WeatherAdapter
import com.jnda.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class WeatherListFragment : BaseFragment<FragmentWeatherListBinding>() {

    private val homeViewModel: HomeViewModel by activityViewModel()
    private var weatherAdapter: WeatherAdapter? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentWeatherListBinding =
        FragmentWeatherListBinding.inflate(inflater, container, attachToParent)

    override fun observeViewModels() {
        homeViewModel.weatherItemDTO.observe(viewLifecycleOwner) { items ->
            weatherAdapter?.replaceItems(items)
        }
    }

    override fun onViewCreated() {
        weatherAdapter = WeatherAdapter(arrayListOf())

        binding.rvWeather.adapter = weatherAdapter

        homeViewModel.getWeathers()
    }
}