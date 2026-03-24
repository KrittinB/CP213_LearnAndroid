package com.example.a157lablearnandroid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SensorViewModel(application: Application) : AndroidViewModel(application) {
    private val sensorTracker = SensorTracker(application)

    private val _accelerometerData = MutableStateFlow(floatArrayOf(0f, 0f, 0f))
    val accelerometerData: StateFlow<FloatArray> = _accelerometerData.asStateFlow()

    private val _locationData = MutableStateFlow<Pair<Double, Double>?>(null)
    val locationData: StateFlow<Pair<Double, Double>?> = _locationData.asStateFlow()

    fun startTrackingAccelerometer() {
        sensorTracker.startListeningAccelerometer { values ->
            _accelerometerData.value = values
        }
    }

    fun stopTrackingAccelerometer() {
        sensorTracker.stopListeningAccelerometer()
    }

    fun startTrackingLocation() {
        sensorTracker.startListeningLocation { location ->
            _locationData.value = Pair(location.latitude, location.longitude)
        }
    }

    fun stopTrackingLocation() {
        sensorTracker.stopListeningLocation()
    }
    
    override fun onCleared() {
        super.onCleared()
        stopTrackingAccelerometer()
        stopTrackingLocation()
    }
}
