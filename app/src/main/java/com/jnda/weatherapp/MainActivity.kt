package com.jnda.weatherapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jnda.home.provider.LocationProvider
import com.jnda.home.viewmodel.LocationViewModel
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    val locationProvider: LocationProvider by inject()
    val locationViewModel: LocationViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.any { p -> p.value }) {
            // revalidate permission
            checkLocationPermission()
        } else {
            // show permission dialog
            // init default location
            locationProvider.getDefaultLocation()
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("To get the actual weather update to your location, please allow the app's location permission.")
                .setTitle("Permission Required")
                .setPositiveButton("OK") { dialog, which ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data = Uri.parse("package:com.jnda.weatherapp")
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss()}
            val dialog = builder.create()
            dialog.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContentView(R.layout.activity_main)

        locationViewModel.checkLocationPermission.observe(this) { isCheck ->
            if (isCheck != null) {
                if (isCheck) {
                    locationViewModel.resetLocationPermission()
                    checkLocationPermission()
                }
            }
        }
        locationViewModel.isLocationUpdateStarted.observe(this) { isStarted ->
            if (isStarted != null) {
                if (isStarted) {
                    locationProvider.startLocationUpdates()
                } else {
                    locationProvider.stopLocationUpdates()
                }
            }
        }
        locationProvider.addOnLocationResult { dto ->
            // update user's location
            locationViewModel.updateLocation(dto)
        }
    }

    private fun checkLocationPermission() {
        if (locationProvider.isLocationPermissionGranted()) {
            if (locationProvider.isInitialized()) {
                locationViewModel.startLocationUpdates()
            } else {
                locationProvider.initLocationProvider()
            }
        } else {
            locationPermissionRequest.launch(locationProvider.getLocationPermissions())
        }
    }
}