package com.example.a157lablearnandroid

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

class SensorActivity : ComponentActivity() {
    private val viewModel: SensorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current
            
            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START) {
                        viewModel.startTrackingAccelerometer()
                    } else if (event == Lifecycle.Event.ON_STOP) {
                        viewModel.stopTrackingAccelerometer()
                        viewModel.stopTrackingLocation()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }

            SensorScreen(viewModel)
        }
    }
}

@Composable
fun SensorScreen(viewModel: SensorViewModel) {
    val accelerometerData by viewModel.accelerometerData.collectAsState()
    val locationData by viewModel.locationData.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                viewModel.startTrackingLocation()
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Hardware Sensors & MVVM", fontSize = 24.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(bottom = 32.dp))
        
        Text("Accelerometer Data", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text("X: ${"%.2f".format(accelerometerData[0])}", fontSize = 16.sp)
        Text("Y: ${"%.2f".format(accelerometerData[1])}", fontSize = 16.sp)
        Text("Z: ${"%.2f".format(accelerometerData[2])}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text("(Updates automatically)", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 32.dp))
        
        Text("GPS Location:", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
        if (locationData != null) {
            Text("Lat: ${locationData?.first}", fontSize = 16.sp)
            Text("Lng: ${locationData?.second}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 24.dp))
        } else {
            Text("Lat: -", fontSize = 16.sp)
            Text("Lng: -", fontSize = 16.sp, modifier = Modifier.padding(bottom = 24.dp))
        }

        Button(
            onClick = {
                val hasFineLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                val hasCoarseLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                if (hasFineLocation && hasCoarseLocation) {
                    viewModel.startTrackingLocation()
                } else {
                    permissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
                }
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Start Tracking Location")
        }

        Button(
            onClick = {
                viewModel.stopTrackingLocation()
            }
        ) {
            Text("Stop Tracking Location")
        }
    }
}
