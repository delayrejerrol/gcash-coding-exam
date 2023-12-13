package com.jnda.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jnda.home.data.repository.WeatherRepository
import com.jnda.home.domain.dto.WeatherDTO
import com.jnda.storage.dao.WeatherDAO
import com.jnda.storage.data.entity.WeatherEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertContains
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule(UnconfinedTestDispatcher())

    private lateinit var weatherDao: WeatherDAO
    @Mock
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        weatherDao = MockWeatherDAO()
        homeViewModel = HomeViewModel(
            weatherRepository, weatherDao
        )
    }

    @Test
    fun `get weather update then verify weather temperature`() {
        runBlocking {
            val response = createSuccessResponse()
            whenever(weatherRepository.getWeather("14.5496299", "121.0470886")).thenReturn(response)
            homeViewModel.getWeather("14.5496299", "121.0470886")
            verify(homeViewModel.weatherDTO) {
                assertEquals("24°", this.mock.value?.temp)
            }
        }
    }

    @Test
    fun `get list of weathers`() {
        runBlocking {
            homeViewModel.getWeathers()
            verify(homeViewModel.weatherItemDTO) {
                val dto = this.mock.value?.find { t -> t.temp == "1" }
                print(this.mock.value)
                assertEquals("1", dto?.temp)
            }
        }
    }

    private fun createSuccessResponse() : WeatherDTO? {
        return WeatherDTO(
            temp = "24°",
            sunsetTime = "1234",
            sunriseTime = "1234",
            icon = "12n"
        )
    }
}

class MockWeatherDAO : WeatherDAO {
    override fun getWeathers(): Flow<List<WeatherEntity>> {
        val a = arrayListOf<WeatherEntity>()
        for (i in 1..10) {
            a.add(WeatherEntity(
                id = i,
                weatherTemp = "$i",
                dateTime = "1",
                description = "1",
                icon = "1"
            ))
        }
        return flow {
            this.emit(a)
        }
    }

    override suspend fun insert(weatherEntity: WeatherEntity) {

    }
}