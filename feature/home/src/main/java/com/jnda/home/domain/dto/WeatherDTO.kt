package com.jnda.home.domain.dto

import com.jnda.common.orZero
import com.jnda.home.data.response.WeatherResponse
import java.text.DecimalFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

data class WeatherDTO(
    val temp: String,
    val sunriseTime: String = "",
    val sunsetTime: String = "",
    val icon: String? = null
)

fun WeatherResponse.toDTO() : WeatherDTO {
    return WeatherDTO(
        temp = formatTemp(this.mainWeather.temp.orZero()),
        sunriseTime = getTime(this.weatherSys.sunrise ?: 0L, this.timeZone),
        sunsetTime = getTime(this.weatherSys.sunset ?: 0L, this.timeZone),
        icon = weathers?.get(0)?.icon
    )
}

private fun getTime(time: Long, timezone: Int): String {
    val instant = Instant.ofEpochSecond(time + timezone).atOffset(ZoneOffset.UTC)
    return instant.toLocalDateTime().format(
        DateTimeFormatter.ofPattern("hh:mm a")
            .withLocale(Locale.ENGLISH)
    )
}

private fun formatTemp(temp: Double) : String {
    val numberFormat = DecimalFormat("#0")
    return numberFormat.format(temp) + "°"
}
