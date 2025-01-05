package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.providers.LocationProvider

class WelcomeViewModelFactory(
    private val locationProvider: LocationProvider
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(locationProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
