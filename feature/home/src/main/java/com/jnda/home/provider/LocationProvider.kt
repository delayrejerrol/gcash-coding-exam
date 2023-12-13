package com.jnda.home.provider

import com.jnda.home.domain.dto.UserLocationDTO

interface LocationProvider {
    fun initLocationProvider()
    fun getDefaultLocation()
    fun isInitialized(): Boolean
    fun isLocationPermissionGranted(): Boolean
    fun getLocationPermissions(): Array<String>
    fun getLastKnownLocation()
    fun startLocationUpdates()
    fun stopLocationUpdates()
    fun getLatitude(): Double
    fun getLongitude(): Double
    fun addOnLocationResult(onLocationResult: (dto: UserLocationDTO) -> Unit)
}