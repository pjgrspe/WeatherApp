package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.providers.LocationProvider

class WelcomeViewModel(private val locationProvider: LocationProvider) : ViewModel() {

    private val _latitude = MutableStateFlow("")
    val latitude: StateFlow<String> = _latitude

    private val _longitude = MutableStateFlow("")
    val longitude: StateFlow<String> = _longitude

    fun getCurrentLocation() {
        viewModelScope.launch {
            locationProvider.getLastLocation { location ->
                if (location != null) {
                    _latitude.value = location.latitude.toString()
                    _longitude.value = location.longitude.toString()
                    Log.d("WelcomeViewModel", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                } else {
                    _latitude.value = ""
                    _longitude.value = ""
                    Log.d("WelcomeViewModel", "Location is unavailable")
                }
            }
        }
    }
}