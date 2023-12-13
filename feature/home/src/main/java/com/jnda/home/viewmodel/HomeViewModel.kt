package com.jnda.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jnda.common.viewmodel.BaseViewModel
import com.jnda.home.data.repository.WeatherRepository
import com.jnda.home.domain.dto.WeatherDTO
import com.jnda.home.domain.dto.WeatherItemDTO
import com.jnda.home.state.LocationState
import com.jnda.home.state.WeatherRequestState
import com.jnda.storage.dao.WeatherDAO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val weatherRepository: WeatherRepository,
    private val weatherDAO: WeatherDAO,
) : BaseViewModel() {

    private val _weatherDTO: MutableLiveData<WeatherDTO?> by lazy { MutableLiveData() }
    val weatherDTO: LiveData<WeatherDTO?> = _weatherDTO

    private val _weatherItemDTO: MutableLiveData<ArrayList<WeatherItemDTO>> by lazy { MutableLiveData() }
    val weatherItemDTO: LiveData<ArrayList<WeatherItemDTO>> = _weatherItemDTO

    private val _locationState: MutableLiveData<LocationState> by lazy { MutableLiveData() }
    val locationState: LiveData<LocationState> = _locationState

    private val _weatherRequestState: MutableLiveData<WeatherRequestState> by lazy { MutableLiveData(WeatherRequestState.DEFAULT) }
    val weatherRequestState: LiveData<WeatherRequestState> = _weatherRequestState

    fun getWeather(lat: String, lng: String) {
        makeRequest {
            weatherRepository.getWeather(lat, lng).also { result ->
                _weatherDTO.postValue(result)
            }
        }
    }

    fun refreshWeather(lat: String, lng: String) {
        makeRequest {
            weatherRepository.refreshWeather(lat, lng).also { result ->
                _weatherDTO.postValue(result)
            }
        }
    }

    fun requestWeatherState(state: WeatherRequestState) {
        _weatherRequestState.postValue(state)
    }

    fun getWeathers() {
        makeRequest {
            weatherDAO.getWeathers().onEach { list ->
                if (list.isNotEmpty()) {
                    val a = list.map { l ->
                        WeatherItemDTO(
                            l.weatherTemp,
                            l.dateTime,
                            l.description,
                            l.icon,
                        )
                    }
                    _weatherItemDTO.postValue(a as ArrayList<WeatherItemDTO>?)
                }
            }.collect()
        }
    }

}