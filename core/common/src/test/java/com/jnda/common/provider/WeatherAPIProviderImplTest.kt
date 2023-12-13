package com.jnda.common.provider

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.test.assertTrue

class WeatherAPIProviderImplTest {
    private lateinit var weatherAPIProvider: WeatherAPIProvider

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        weatherAPIProvider = MockWeatherAPIProviderImpl()
    }

    @Test
    fun `weather api key should not empty`() {
        weatherAPIProvider.getKey().apply {
            assertTrue(this.isNotEmpty())
        }
    }
}

class MockWeatherAPIProviderImpl : WeatherAPIProvider {
    override fun getKey(): String {
        return "api_key"
    }
}