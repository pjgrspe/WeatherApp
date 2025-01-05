package ph.edu.auf.gorospe.patrickjason.weatherapp.data.providers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat

class LocationProvider(private val context: Context) {

    @SuppressLint("MissingPermission")
    fun getLastLocation(onLocationResult: (Location?) -> Unit) {
        if (hasLocationPermission()) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastKnownLocation != null) {
                Log.d("LocationProvider", "Last known location: ${lastKnownLocation.latitude}, ${lastKnownLocation.longitude}")
                onLocationResult(lastKnownLocation)
            } else {
                Log.d("LocationProvider", "Last known location is null, requesting current location")
                requestCurrentLocation(locationManager, onLocationResult)
            }
        } else {
            Log.d("LocationProvider", "Location permission not granted")
            onLocationResult(null)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestCurrentLocation(locationManager: LocationManager, onLocationResult: (Location?) -> Unit) {
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.d("LocationProvider", "Current location: ${location.latitude}, ${location.longitude}")
                onLocationResult(location)
                locationManager.removeUpdates(this)
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}