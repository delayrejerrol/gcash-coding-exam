package com.jnda.home.data.repository

import com.jnda.common.provider.WeatherAPIProvider
import com.jnda.home.data.WeatherService
import com.jnda.home.data.response.WeatherMain
import com.jnda.home.data.response.WeatherResponse
import com.jnda.home.data.response.WeatherSys
import com.jnda.network.provider.NetworkProvider
import com.jnda.network.provider.NetworkProviderImpl
import com.jnda.storage.dao.WeatherDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {
    @Mock
    private lateinit var weatherService: WeatherService
    @Mock
    private lateinit var weatherDao: WeatherDAO

    private lateinit var networkProvider: NetworkProvider
    private lateinit var weatherAPIProvider: WeatherAPIProvider
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        networkProvider = NetworkProviderImpl(UnconfinedTestDispatcher())
        weatherAPIProvider = MockWeatherAPIProvider()
        weatherRepository = WeatherRepositoryImpl(
            weatherService, weatherDao, networkProvider, UnconfinedTestDispatcher(),
            weatherAPIProvider
        )
    }

    @Test
    fun `get weather update`() {
        val map = HashMap<String, Any>()
        map["lat"] = 14.5496299
        map["lon"] = 121.0470886
        map["units"] = "metric"
        map["appid"] = weatherAPIProvider.getKey()

        val mockResponse = createSuccessResponse()

        runBlocking {
            whenever(weatherService.getWeather(map)).thenReturn(mockResponse)
            weatherRepository.getWeather("14.5496299", "121.0470886").apply {
                assertEquals("24°", this?.temp)
            }
        }
    }

    private fun createSuccessResponse(): WeatherResponse {
        val weatherMain = WeatherMain(24.0, 24.0, 24.0)
        val weatherSys = WeatherSys(1, 1, "Philippines", 1L, 1L)
        return WeatherResponse(
            weathers = null,
            base = "",
            mainWeather = weatherMain,
            weatherSys = weatherSys,
            timeZone = 1
        )
    }
}

class MockWeatherAPIProvider : WeatherAPIProvider {
    override fun getKey(): String {
        return "mock_api_key"
    }
}