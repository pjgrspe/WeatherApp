package ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.interfaces

import ph.edu.auf.gorospe.patrickjason.weatherapp.api.models.hourly.HourlyModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.APIConstants
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.current.WeatherModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.daily.DailyModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.geocoding.GeocodeModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.geocoding.GeocodingResult
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIService {

    @GET(APIConstants.GET_CURRENT_WEATHER)
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String = APIConstants.API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherModel

    @GET(APIConstants.GET_HOURLY_FORECAST)
    suspend fun getHourlyForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String = APIConstants.API_KEY,
        @Query("units") units: String = "metric"
    ): HourlyModel

    @GET(APIConstants.GET_DAILY_FORECAST)
    suspend fun getDailyForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String = APIConstants.API_KEY,
        @Query("units") units: String = "metric",
        @Query("cnt") count: Int = 7 // Number of days to forecast (default 7)
    ): DailyModel

    @GET(APIConstants.GET_GEOCODING)
    suspend fun searchLocations(
        @Query("q") query: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String = APIConstants.API_KEY
    ): List<GeocodingResult> // Changed to List<GeocodingResult>
}