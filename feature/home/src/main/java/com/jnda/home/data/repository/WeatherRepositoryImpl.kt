package com.jnda.home.data.repository

import com.jnda.common.provider.WeatherAPIProvider
import com.jnda.home.data.WeatherService
import com.jnda.home.domain.dto.WeatherDTO
import com.jnda.home.domain.dto.toDTO
import com.jnda.network.data.model.ServiceResult
import com.jnda.network.di.NetworkComponent
import com.jnda.network.provider.NetworkProvider
import com.jnda.storage.dao.WeatherDAO
import com.jnda.storage.data.entity.WeatherEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import java.util.Date

@Factory
class WeatherRepositoryImpl(
    private val weatherService: WeatherService,
    private val weatherDAO: WeatherDAO,
    private val networkProvider: NetworkProvider,
    @Named(NetworkComponent.DISPATCHER_IO) val ioDispatcher: CoroutineDispatcher,
    private val weatherAPIProvider: WeatherAPIProvider
) : WeatherRepository {
    override suspend fun getWeather(latitude: String?, longitude: String?) : WeatherDTO? {
        val map = HashMap<String, Any>()
        map["lat"] = latitude?.toDouble() ?: "0.0"
        map["lon"] = longitude?.toDouble() ?: "0.0"
        map["units"] = "metric"
        map["appid"] = weatherAPIProvider.getKey()

        var result: WeatherDTO? = null
        networkProvider.requestDefault {
            weatherService.getWeather(map)
        }.also { serviceResult ->
            if (serviceResult is ServiceResult.Success) {
                result = serviceResult.data?.toDTO()

                val weatherEntity = WeatherEntity(
                    weatherTemp = result?.temp ?: "",
                    dateTime = Date().time.toString(),
                    description = serviceResult.data?.weathers?.get(0)?.description ?: "",
                    icon = result?.icon ?: ""
                )

                runBlocking(ioDispatcher) {
                    weatherDAO.insert(weatherEntity)
                }
            }
        }
        return result
    }

    override suspend fun refreshWeather(latitude: String?, longitude: String?): WeatherDTO? {
        val map = HashMap<String, Any>()
        map["lat"] = latitude?.toDouble() ?: "0.0"
        map["lon"] = longitude?.toDouble() ?: "0.0"
        map["units"] = "metric"
        map["appid"] = weatherAPIProvider.getKey()

        var result: WeatherDTO? = null
        networkProvider.requestDefault {
            weatherService.getWeather(map)
        }.also { serviceResult ->
            if (serviceResult is ServiceResult.Success) {
                result = serviceResult.data?.toDTO()
            }
        }
        return result
    }
}