package com.klintsoft.location

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationViewModel(): ViewModel() {

    private lateinit var locationManager: LocationManager
    private lateinit var geocoder: Geocoder

    private val _ui = mutableStateOf(UiState())
    val ui get() = _ui.value

    init {
        Log.i("MyInfo", "init block started")
        Log.i("MyInfo", "init block ended")
    }

    fun initLocationManager(ctx: Context) {
        val timeInterval = 1000L
        locationManager = LocationManager(ctx, timeInterval, 0f, refresh = { refreshLocation(it) })
        geocoder = Geocoder(ctx)
    }

    fun startLocationTracking() {
        locationManager.startLocationTracking()
    }

    fun stopLocationTracking() {
        locationManager.stopLocationTracking()
    }

    fun refreshLocation(location: Location) {
        _ui.value = ui.copy(currentLocation = location)
        getGeoLocation()
    }

    fun getGeoLocation() {
        val firstAddress = ui.currentLocation?.let {
            geocoder.getFromLocation(it.latitude, it.longitude, 1)
        }
        if (firstAddress != null && firstAddress.isNotEmpty()) {
            with(firstAddress.first()) {
                val geoInfo = GeoLocationInfo(
                    countryName = countryName,
                    postalCode = postalCode,
                    locality = locality,
                    streetName = thoroughfare,
                    streetNumber = subThoroughfare,
                    adminArea = adminArea
                )
                _ui.value = ui.copy(geoInfo = geoInfo)
            }
        }
    }
}