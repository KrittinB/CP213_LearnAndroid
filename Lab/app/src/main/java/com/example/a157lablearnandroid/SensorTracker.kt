package com.example.a157lablearnandroid

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

class SensorTracker(context: Context) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    
    // For Location
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var onAccelerometerMeasured: ((FloatArray) -> Unit)? = null
    private var onLocationMeasured: ((Location) -> Unit)? = null

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                onAccelerometerMeasured?.invoke(event.values.clone())
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            onLocationMeasured?.invoke(location)
        }
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun startListeningAccelerometer(listener: (FloatArray) -> Unit) {
        onAccelerometerMeasured = listener
        accelerometer?.let {
            sensorManager.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListeningAccelerometer() {
        sensorManager.unregisterListener(sensorEventListener)
        onAccelerometerMeasured = null
    }

    @SuppressLint("MissingPermission") // Permission handled in UI
    fun startListeningLocation(listener: (Location) -> Unit) {
        onLocationMeasured = listener
        // Request from GPS
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000L, 1f, locationListener)
        }
        // Also Network provider for faster but less accurate results in emulator
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000L, 1f, locationListener)
        }
    }

    fun stopListeningLocation() {
        locationManager.removeUpdates(locationListener)
        onLocationMeasured = null
    }
}
