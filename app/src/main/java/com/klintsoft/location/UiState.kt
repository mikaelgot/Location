package com.klintsoft.location

import android.location.Location

data class UiState(
    val currentLocation: Location? = null,
    val geoInfo: GeoLocationInfo? = null
)

data class GeoLocationInfo(
    val countryName: String = "",
    val postalCode: String = "",
    val locality: String = "",
    val streetName: String = "",
    val adminArea: String = "",
    val streetNumber: String = ""
)