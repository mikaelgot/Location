package com.klintsoft.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.*
import com.klintsoft.location.ui.theme.LocationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LocationApp()
                }
            }
        }
    }
}

@Composable
fun LocationApp() {
    val vm : LocationViewModel = viewModel()
    val ui = vm.ui
    val ctx = LocalContext.current

    LaunchedEffect(Unit){
        vm.initLocationManager(ctx)
        vm.startLocationTracking()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        if (ui.currentLocation != null) {
            Text(text = "Location", style = MaterialTheme.typography.h6, fontWeight = FontWeight.SemiBold)
            Text(text = "")
            Text(text = "Latitude: ${ui.currentLocation.latitude}")
            Text(text = "Longitude: ${ui.currentLocation.longitude}")
            Text(text = "")
            if (ui.geoInfo != null) {
                Text(text = "${ui.geoInfo.streetName} ${ui.geoInfo.streetNumber}, ")
                Text(text = "${ui.geoInfo.postalCode}, ${ui.geoInfo.locality}")
                Text(text = "${ui.geoInfo.adminArea}, ${ui.geoInfo.countryName}")
            }
        }
        else{
            Text(text = "Finding current location...")
        }
    }


}