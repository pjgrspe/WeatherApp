package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ph.edu.auf.gorospe.patrickjason.weatherapp.api.models.hourly.HourlyItem0
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.RetrofitFactory
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.current.WeatherModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.daily.DailyModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.models.geocoding.GeocodingResult
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.repositories.WeatherRepository
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main.components.CurrentWeatherCard
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main.components.HourlyForecastSection
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main.components.LocationHeader
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main.components.WeatherDetailsGrid
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main.components.WeeklyTemperatureChart
import ph.edu.auf.gorospe.patrickjason.weatherapp.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    latitude: String,
    longitude: String
) {
    val apiService = RetrofitFactory.weatherAPIService
    val geocodingAPIService = RetrofitFactory.geocodingAPIService
    val repository = WeatherRepository(apiService, geocodingAPIService)
    val viewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(repository, latitude, longitude)
    )
    val state by viewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppTheme.colorScheme.background
    ) {
        when {
            state.isLoading -> LoadingIndicator()
            state.error != null -> ErrorContent(
                error = state.error!!, onRetry = { viewModel.loadWeatherData() }
            )
            state.currentWeather != null -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = AppTheme.sizes.medium)
                ) {
                    item {
                        LocationHeader(
                            location = "${state.currentWeather?.name}, ${state.currentWeather?.sys?.country}",
                            date = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault()).format(
                                Date()
                            ),
                            viewModel = viewModel,
                            onBackClick = { navController.navigateUp() },
                            onSearchLocation = { query ->
                                viewModel.searchLocation(query)
                            }
                        )
                    }
                    item {
                        CurrentWeatherCard(
                            temperature = state.currentWeather?.main?.temp?.toInt() ?: 0,
                            condition = state.currentWeather?.weather?.firstOrNull()?.main ?: "Unknown",
                            weatherIcon = state.currentWeather?.weather?.firstOrNull()?.icon ?: ""
                        )
                    }
                    item {
                        state.hourlyForecast.let { hourlyForecast ->
                            if (hourlyForecast.isNotEmpty()) {
                                HourlyForecastSection(
                                    hourlyItems = hourlyForecast,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    item {
                        WeatherDetailsGrid(
                            windSpeed = state.currentWeather?.wind?.speed ?: 0f,
                            uvIndex = 0,
                            humidity = state.currentWeather?.main?.humidity ?: 0,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        WeeklyTemperatureChart(
                            temperatures = weeklyTemperatures,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

val weeklyTemperatures = listOf(
    Pair("Mon", 25),
    Pair("Tue", 28),
    Pair("Wed", 26),
    Pair("Thu", 24),
    Pair("Fri", 27),
    Pair("Sat", 29),
    Pair("Sun", 26)
)

@Composable
private fun ErrorContent(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.sizes.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = error,
            color = AppTheme.colorScheme.onError,
            style = AppTheme.typography.body1
        )
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = AppTheme.sizes.medium),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colorScheme.primaryDark
            )
        ) {
            Text("Retry", color = AppTheme.colorScheme.onBackground)
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun SearchResultsList(
    results: List<GeocodingResult>,
    onLocationSelected: (GeocodingResult) -> Unit
) {
    LazyColumn {
        items(results) { result ->
            SearchResultItem(result) {
                onLocationSelected(result)
            }
        }
    }
}

@Composable
fun SearchResultItem(
    result: GeocodingResult,
    onLocationClick: () -> Unit
) {
    Button(
        onClick = { onLocationClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.sizes.small)
    ) {
        Text(
            text = "${result.name}, ${result.state ?: ""}, ${result.country}",
            style = AppTheme.typography.body1
        )
    }
}

data class WeatherScreenState(
    val isLoading: Boolean = true,
    val currentWeather: WeatherModel? = null,
    val hourlyForecast: List<HourlyItem0> = emptyList(),
    val dailyForecast: DailyModel? = null,
    val error: String? = null,
    val searchResults: List<GeocodingResult> = emptyList(),
    val searchQuery: String = ""
)