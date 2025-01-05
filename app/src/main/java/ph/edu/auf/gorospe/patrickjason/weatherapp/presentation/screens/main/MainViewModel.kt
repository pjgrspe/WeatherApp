package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ph.edu.auf.gorospe.patrickjason.weatherapp.api.models.hourly.HourlyItem0
import ph.edu.auf.gorospe.patrickjason.weatherapp.api.models.hourly.HourlyRain
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.geocoding.GeocodingResult
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.repositories.WeatherRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val latitude: String,
    private val longitude: String
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherScreenState())
    val state = _state.asStateFlow()

    init {
        loadWeatherData()
    }

    fun loadWeatherData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                if (latitude.isEmpty() || longitude.isEmpty()) {
                    throw IllegalArgumentException("Latitude and Longitude must not be empty")
                }

                val currentWeather = repository.fetchCurrentWeather(latitude, longitude)
                val hourlyForecast = repository.fetchHourlyForecast(latitude, longitude)

                // Filter and interpolate for hourly forecast
                val currentTime = System.currentTimeMillis() / 1000 // Current time in seconds
                val forecastHours = hourlyForecast.list
                    ?.filter { it.dt >= currentTime } // Keep only entries from now onwards
                    ?.take(24) // Limit to the first 24 entries
                    ?.mapIndexed { index, hourlyItem ->
                        // Interpolate to generate hourly data (1-hour steps if needed)
                        val newTime = currentTime + (index * 3600) // Increment by 3600 seconds (1 hour)
                        HourlyItem0(
                            dt = newTime,
                            dtTxt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                .format(Date(newTime * 1000)), // Format new timestamp as string
                            main = hourlyItem.main, // Copy existing data
                            weather = hourlyItem.weather, // Copy existing data
                            clouds = hourlyItem.clouds, // Copy existing data
                            pop = hourlyItem.pop, // Probability of precipitation
                            rain = hourlyItem.rain ?: HourlyRain(0.0), // Handle nullable rain field
                            sys = hourlyItem.sys, // Copy existing data
                            visibility = hourlyItem.visibility, // Copy existing data
                            wind = hourlyItem.wind // Copy existing data
                        )
                    } ?: emptyList()

                _state.value = _state.value.copy(
                    isLoading = false,
                    currentWeather = currentWeather,
                    hourlyForecast = forecastHours,
                    dailyForecast = repository.fetchDailyForecast(latitude, longitude)
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "An unexpected error occurred"
                )
            }
        }
    }


    fun searchLocation(query: String) {
        viewModelScope.launch {
            try {
                // Perform search only if the query is not empty
                if (query.isNotEmpty()) {
                    val searchResults: List<GeocodingResult> = repository.searchLocations(query)
                    _state.value = _state.value.copy(
                        searchResults = searchResults,
                        searchQuery = query
                    )
                } else {
                    // Clear search results if the query is empty
                    _state.value = _state.value.copy(
                        searchResults = emptyList(),
                        searchQuery = query
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error searching location: ${e.message}"
                )
            }
        }
    }

    // New function to clear search results
    fun clearSearchResults() {
        _state.value = _state.value.copy(searchResults = emptyList())
    }

    fun updateLocation(newLatitude: String, newLongitude: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                // Update the new location coordinates
                val currentWeather = repository.fetchCurrentWeather(newLatitude, newLongitude)
                val hourlyForecast = repository.fetchHourlyForecast(newLatitude, newLongitude)
                val dailyForecast = repository.fetchDailyForecast(newLatitude, newLongitude)

                _state.value = _state.value.copy(
                    isLoading = false,
                    currentWeather = currentWeather,
                    hourlyForecast = hourlyForecast.list ?: emptyList(),
                    dailyForecast = dailyForecast
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error fetching data for new location"
                )
            }
        }
    }
}