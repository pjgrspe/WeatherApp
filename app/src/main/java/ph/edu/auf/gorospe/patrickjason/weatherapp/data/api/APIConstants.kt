package ph.edu.auf.gorospe.patrickjason.weatherapp.data.api

object APIConstants {
    const val WEATHER_API_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val GEO_API_BASE_URL = "https://api.openweathermap.org/geo/1.0/" // New base URL for geocoding
    const val API_KEY = ":P"
    const val WEATHER_ICON_BASE_URL = "https://openweathermap.org/img/wn/"

    // Endpoints
    const val GET_CURRENT_WEATHER = "weather"
    const val GET_HOURLY_FORECAST = "forecast"
    const val GET_DAILY_FORECAST = "forecast/daily"
    const val GET_GEOCODING = "direct"
}