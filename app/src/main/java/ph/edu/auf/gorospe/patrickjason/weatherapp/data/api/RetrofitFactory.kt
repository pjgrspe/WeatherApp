// RetrofitFactory.kt
package ph.edu.auf.gorospe.patrickjason.weatherapp.data.api

import okhttp3.OkHttpClient
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.interfaces.WeatherAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    private val client = OkHttpClient.Builder().build()

    val weatherAPIService: WeatherAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(APIConstants.WEATHER_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPIService::class.java)
    }

    val geocodingAPIService: WeatherAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(APIConstants.GEO_API_BASE_URL) // Use the new base URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPIService::class.java)
    }
}