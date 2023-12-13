package com.jnda.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.jnda.common.loadWeatherIcon
import com.jnda.common.view.BaseFragment
import com.jnda.home.databinding.FragmentMainWeatherBinding
import com.jnda.home.state.WeatherRequestState
import com.jnda.home.viewmodel.HomeViewModel
import com.jnda.home.viewmodel.LocationViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainWeatherFragment : BaseFragment<FragmentMainWeatherBinding>() {

    private val homeViewModel: HomeViewModel by activityViewModel()
    private val locationViewModel: LocationViewModel by activityViewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentMainWeatherBinding =
        FragmentMainWeatherBinding.inflate(inflater, container, attachToParent)

    override fun observeViewModels() {
        locationViewModel.userLocation.observe(viewLifecycleOwner) { result ->
            binding.tvLocation.text = "${result.city}, ${result.countryCode}"
        }
        homeViewModel.weatherDTO.observe(viewLifecycleOwner) { dto ->
            if (dto != null) {
                binding.ivMainWeather.loadWeatherIcon(dto.icon)
                binding.tvWeatherTemp.text = dto.temp
                binding.tvSunsetTime.text = dto.sunsetTime
                binding.tvSunriseTime.text = dto.sunriseTime
            } else {
                Toast.makeText(requireContext(), "Unable to get weather update.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onViewCreated() {
        binding.ivRefresh.setOnClickListener {
            homeViewModel.requestWeatherState(WeatherRequestState.REFRESH)
        }
    }
}