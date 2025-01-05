package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.repositories.WeatherRepository

class WeatherViewModelFactory(
    private val repository: WeatherRepository,
    private val latitude: String,
    private val longitude: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository, latitude, longitude) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
