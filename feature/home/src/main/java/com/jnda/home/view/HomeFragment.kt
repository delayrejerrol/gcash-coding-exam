package com.jnda.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.jnda.common.view.BaseFragment
import com.jnda.home.R
import com.jnda.home.databinding.FragmentHomeBinding
import com.jnda.home.state.WeatherRequestState
import com.jnda.home.viewmodel.HomeViewModel
import com.jnda.home.viewmodel.LocationViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by activityViewModel()
    private val locationViewModel: LocationViewModel by activityViewModel()

    private lateinit var navController : NavController

    private enum class Tab {
        MAIN, LIST
    }

    private var currentTab: Tab = Tab.MAIN

    private var isWeatherRefresh: Boolean = false

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, attachToParent)

    override fun observeViewModels() {
        locationViewModel.userLocation.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                if (isWeatherRefresh) {
                    isWeatherRefresh = false
                    homeViewModel.refreshWeather(
                        result.latitude.toString(),
                        result.longitude.toString()
                    )
                } else {
                    homeViewModel.getWeather(
                        result.latitude.toString(),
                        result.longitude.toString()
                    )
                }
                locationViewModel.stopLocationUpdates()
            }
        }
        homeViewModel.weatherRequestState.observe(viewLifecycleOwner) { state ->
            if (state == WeatherRequestState.REFRESH) {
                isWeatherRefresh = true
                locationViewModel.checkLocationPermission()
                homeViewModel.requestWeatherState(WeatherRequestState.DEFAULT)
            }
        }
    }

    override fun onViewCreated() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_dashboard) as NavHostFragment
        navController = navHostFragment.navController

        locationViewModel.checkLocationPermission()

        binding.tabMain.setOnClickListener {
            if (currentTab == Tab.MAIN) return@setOnClickListener
            currentTab = Tab.MAIN
            binding.ivMain.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.tab_icon_home_active))
            binding.ivList.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.tab_icon_list_inactive))
            navController.popBackStack()
            navController.navigate(navigationProvider.navigateToMainWeatherScreen())
        }
        binding.tabList.setOnClickListener {
            if (currentTab == Tab.LIST) return@setOnClickListener
            currentTab = Tab.LIST
            binding.ivMain.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.tab_icon_home_inactive))
            binding.ivList.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.tab_icon_list_active))
            navController.popBackStack()
            navController.navigate(navigationProvider.navigateToWeatherListScreen())
        }
    }
}