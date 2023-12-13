package com.jnda.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jnda.common.viewmodel.BaseViewModel
import com.jnda.home.domain.dto.UserLocationDTO
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LocationViewModel : BaseViewModel() {
    private val _checkLocationPermission: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val checkLocationPermission: LiveData<Boolean> = _checkLocationPermission

    private val _userLocation: MutableLiveData<UserLocationDTO> by lazy { MutableLiveData() }
    val userLocation: LiveData<UserLocationDTO> = _userLocation

    private val _isLocationUpdateStarted: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val isLocationUpdateStarted: LiveData<Boolean> = _isLocationUpdateStarted

    fun checkLocationPermission() {
        _checkLocationPermission.postValue(true)
    }

    fun resetLocationPermission() {
        _checkLocationPermission.postValue(false)
    }

    fun updateLocation(dto: UserLocationDTO) {
        _userLocation.postValue(dto)
    }

    fun startLocationUpdates() {
        _isLocationUpdateStarted.postValue(true)
    }

    fun stopLocationUpdates() {
        _isLocationUpdateStarted.postValue(false)
    }

    fun clear() {
        _checkLocationPermission.value = null
        _userLocation.value = null
        _isLocationUpdateStarted.value = null
    }
}