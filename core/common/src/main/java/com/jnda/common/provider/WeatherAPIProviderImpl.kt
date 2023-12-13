package com.jnda.common.provider

import org.koin.core.annotation.Single

@Single
class WeatherAPIProviderImpl : WeatherAPIProvider {
    override fun getKey(): String = "" // PUT YOUR OPENWEATHER API HERE
}