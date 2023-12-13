package com.jnda.home.data

import com.jnda.home.data.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {

    @GET("weather")
    suspend fun getWeather(@QueryMap(encoded = true) query: HashMap<String, Any>) :
            WeatherResponse
}