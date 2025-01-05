// WeatherRepository.kt
package ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.repositories

import android.util.Log
import ph.edu.auf.gorospe.patrickjason.weatherapp.api.models.hourly.HourlyModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.RetrofitFactory.geocodingAPIService
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.interfaces.WeatherAPIService
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.current.WeatherModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.daily.DailyModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.geocoding.GeocodeModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.geocoding.GeocodingResult

class WeatherRepository(
    private val apiService: WeatherAPIService,
    private val geocodingAPIService: WeatherAPIService // Add this
){
    suspend fun fetchCurrentWeather(latitude: String, longitude: String): WeatherModel {
        try {
            val response = apiService.getCurrentWeather(latitude, longitude)
            Log.d("WeatherRepository", "CurrentWeatherResponse: $response")
            return response
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching weather", e) // Log errors
            throw e // Re-throw the exception
        }
    }

    suspend fun fetchHourlyForecast(latitude: String, longitude: String): HourlyModel {
        val response = apiService.getHourlyForecast(latitude, longitude)
        Log.d("WeatherRepository", "HourlyForecastResponse: $response")
        return response
    }

    suspend fun fetchDailyForecast(latitude: String, longitude: String): DailyModel {
        val response = apiService.getDailyForecast(latitude, longitude)
        Log.d("WeatherRepository", "DailyForecastResponse: $response")
        return response
    }

    suspend fun searchLocations(query: String): List<GeocodingResult> {
        val response = geocodingAPIService.searchLocations(query) // Use geocodingAPIService here
        return response
    }
}