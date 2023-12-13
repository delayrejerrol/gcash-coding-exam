package com.jnda.home.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherResponse(
    @SerializedName("weather") val weathers: List<Weather>?,
    @SerializedName("base") val base: String?,
    @SerializedName("main") val mainWeather: WeatherMain,
    @SerializedName("sys") val weatherSys: WeatherSys,
    @SerializedName("timezone") val timeZone: Int
)

@Keep
data class Weather(
    val id: Int?,
    val main: String?,
    val description: String?,
    val icon: String?,
)

@Keep
data class WeatherMain(
    @SerializedName("temp") val temp: Double?,
    @SerializedName("temp_min") val minTemp: Double?,
    @SerializedName("temp_max") val maxTemp: Double?
)

@Keep
data class WeatherSys(
    val type: Int?,
    val id: Long?,
    val country: String?,
    val sunrise: Long?,
    val sunset: Long?
)
