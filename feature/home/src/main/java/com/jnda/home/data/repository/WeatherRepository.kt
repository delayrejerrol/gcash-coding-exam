package com.jnda.home.data.repository

import com.jnda.home.domain.dto.WeatherDTO

interface WeatherRepository {
    suspend fun getWeather(latitude: String?, longitude: String?) : WeatherDTO?
    suspend fun refreshWeather(latitude: String?, longitude: String?) : WeatherDTO?
}