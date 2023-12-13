package com.jnda.home.domain.dto

data class UserLocationDTO(
    val latitude: Double,
    val longitude: Double,
    /**
     * A locality from Geocoder Address
     */
    val city: String,
    /**
     * A combination of adminArea and countryCode from Geocoder Address
     */
    val countryCode: String
)
