package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.auf.gorospe.patrickjason.weatherapp.api.models.hourly.HourlyItem0
import ph.edu.auf.gorospe.patrickjason.weatherapp.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import coil.compose.AsyncImage
import ph.edu.auf.gorospe.patrickjason.weatherapp.R
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.api.APIConstants
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.components.textfields.StyledTextField
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationHeader(
    location: String,
    date: String,
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSearchLocation: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (isSearchVisible) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Search bar with back button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    IconButton(
                        onClick = {
                            isSearchVisible = false
                            searchQuery = ""
                            viewModel.clearSearchResults()
                            focusManager.clearFocus()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = AppTheme.colorScheme.onBackground
                        )
                    }

                    ModernSearchBar(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            onSearchLocation(it)
                        },
                        placeholder = "Search location...",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        onIconClick = {
                            // Location pin functionality (optional)
                        }
                    )
                }

                // Dropdown for search results
                DropdownMenu(
                    expanded = state.searchResults.isNotEmpty(),
                    onDismissRequest = { viewModel.clearSearchResults() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.searchResults.forEach { result ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.updateLocation(result.lat.toString(), result.lon.toString())
                                viewModel.clearSearchResults()
                                searchQuery = ""
                                isSearchVisible = false
                                focusManager.clearFocus()
                            },
                            text = {
                                Text(
                                    text = "${result.name}, ${result.state ?: ""}, ${result.country}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Location and date
                Column {
                    Text(
                        text = location,
                        style = MaterialTheme.typography.titleMedium,
                        color = AppTheme.colorScheme.onBackground
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }

                // Search button
                IconButton(
                    onClick = { isSearchVisible = true },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = AppTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}



@Composable
fun ModernSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onIconClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Search Icon
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.DarkGray,
                modifier = Modifier.size(20.dp)
            )

            // Input Field
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = textStyle.copy(color = Color.Black),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = textStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                            )
                        }
                        innerTextField()
                    }
                }
            )

            // Optional Additional Icon (e.g., location pin or clear button)
            if (onIconClick != null) {
                IconButton(
                    onClick = onIconClick,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn, // Replace with relevant icon
                        contentDescription = "Additional Icon",
                        tint = AppTheme.colorScheme.primaryDark
                    )
                }
            }
        }
    }
}



@Composable
fun CurrentWeatherCard(
    temperature: Int,
    condition: String,
    weatherIcon: String?, // Add weather icon parameter
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.sizes.medium) // Use AppTheme.sizes
    ) {
        // Weather Icon
        AsyncImage(
            model = "${APIConstants.WEATHER_ICON_BASE_URL}${weatherIcon}@4x.png",
            contentDescription = condition,
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(AppTheme.sizes.large)) // Use AppTheme.sizes

        // Temperature
        Text(
            text = "${temperature}°C",
            style = AppTheme.typography.h1, // Use AppTheme.typography
            color = AppTheme.colorScheme.onBackground // Use AppTheme.colorScheme
        )

        Spacer(modifier = Modifier.height(AppTheme.sizes.small)) // Use AppTheme.sizes

        // Condition
        Text(
            text = condition,
            style = AppTheme.typography.h3, // Use AppTheme.typography
            color = AppTheme.colorScheme.onBackground.copy(alpha = 0.7f) // Use AppTheme.colorScheme
        )
    }
}

@Composable
fun HourlyForecastSection(
    hourlyItems: List<HourlyItem0>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.medium),
        contentPadding = PaddingValues(
            start = AppTheme.sizes.small,
            end = AppTheme.sizes.small
        )
    ) {
        itemsIndexed(hourlyItems.take(24)) { index, hourlyData ->
            HourlyForecastCard(
                hourlyData = hourlyData,
                modifier = Modifier.padding(
                    start = if (index == 0) AppTheme.sizes.medium else 0.dp,
                    end = if (index == hourlyItems.lastIndex) AppTheme.sizes.medium else 0.dp
                )
            )
        }
    }
}


@Composable
fun HourlyForecastCard(
    hourlyData: HourlyItem0,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = AppTheme.shapes.card, // Use AppTheme.shapes
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.primaryDark // Use AppTheme.colorScheme
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(AppTheme.sizes.medium) // Use AppTheme.sizes
        ) {
            Text(
                text = SimpleDateFormat("HH:mm", Locale.getDefault())
                    .format(Date(hourlyData.dt.toLong() * 1000)),
                style = AppTheme.typography.body2, // Use AppTheme.typography
                color = AppTheme.colorScheme.onBackground // Use AppTheme.colorScheme
            )

            Spacer(modifier = Modifier.height(AppTheme.sizes.small)) // Use AppTheme.sizes

            hourlyData.weather?.firstOrNull()?.icon?.let { icon ->
                AsyncImage(
                    model = "${APIConstants.WEATHER_ICON_BASE_URL}$icon@2x.png",
                    contentDescription = hourlyData.weather?.firstOrNull()?.description,
                    modifier = Modifier.size(AppTheme.sizes.large),
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.sizes.small)) // Use AppTheme.sizes

            Text(
                text = "${hourlyData.main.temp.toInt()}°",
                style = AppTheme.typography.h5, // Use AppTheme.typography
                color = AppTheme.colorScheme.onBackground // Use AppTheme.colorScheme
            )
        }
    }
}


@Composable
fun WeatherDetailsGrid(
    windSpeed: Number,
    uvIndex: Int,
    humidity: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(AppTheme.sizes.medium)) { // Use AppTheme.sizes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherDetailCard(
                icon = ImageVector.vectorResource(id = R.drawable.wind),
                title = "Wind",
                value = "$windSpeed km/h",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(AppTheme.sizes.medium)) // Use AppTheme.sizes
            WeatherDetailCard(
                icon = ImageVector.vectorResource(id = R.drawable.uv_index),
                title = "UV Index",
                value = "$uvIndex",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.sizes.medium)) // Use AppTheme.sizes

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherDetailCard(
                icon = ImageVector.vectorResource(id = R.drawable.humidity),
                title = "Humidity",
                value = "$humidity%",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(AppTheme.sizes.medium)) // Use AppTheme.sizes
            WeatherDetailCard(
                icon = ImageVector.vectorResource(id = R.drawable.visibility),
                title = "Visibility",
                value = "3 km",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun WeatherDetailCard(
    icon: ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = AppTheme.shapes.card,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.primaryDark
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // Vertically align items
            horizontalArrangement = Arrangement.SpaceBetween, // Push icon to the right
            modifier = Modifier
                .padding(AppTheme.sizes.medium)
                .fillMaxWidth() // Occupy full width for better balance
        ) {
            Column(
                modifier = Modifier.weight(1f) // Allow text to occupy available space
            ) {
                Text(
                    text = value,
                    style = AppTheme.typography.h5,
                    color = AppTheme.colorScheme.onBackground
                )

                Text(
                    text = title,
                    style = AppTheme.typography.body2,
                    color = AppTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppTheme.colorScheme.onBackground,
                modifier = Modifier.size(AppTheme.sizes.xLarge)
            )
        }
    }
}

@Composable
fun WeeklyTemperatureChart(
    temperatures: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    val maxTemp = (temperatures.maxOfOrNull { it.second } ?: 0) + 1
    val minTemp = (temperatures.minOfOrNull { it.second } ?: 0) - 1
    val tempRange = maxTemp - minTemp

    // Precompute values in a @Composable context
    val textSize = with(LocalDensity.current) { AppTheme.typography.body2.fontSize.toPx() }
    val textColor = AppTheme.colorScheme.onPrimary.toArgb()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(AppTheme.sizes.medium),
        shape = AppTheme.shapes.card,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.primaryDark
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.sizes.medium)
        ) {
            Text(
                text = "Weekly Temperatures",
                style = AppTheme.typography.body1,
                color = AppTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = AppTheme.sizes.medium)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = AppTheme.sizes.small)
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val barWidth = canvasWidth / temperatures.size

                    // Draw grid lines
                    val horizontalGridLines = 4
                    for (i in 0..horizontalGridLines) {
                        val y = i * canvasHeight / horizontalGridLines
                        drawLine(
                            color = Color.Black.copy(alpha = 0.2f),
                            start = Offset(0f, y),
                            end = Offset(canvasWidth, y),
                            strokeWidth = 1f
                        )
                    }

                    // Draw temperature lines and dots
                    val path = Path()
                    temperatures.forEachIndexed { index, (_, temp) ->
                        val x = barWidth * (index + 0.5f)
                        val y = canvasHeight - ((temp - minTemp) / tempRange.toFloat()) * canvasHeight

                        drawCircle(
                            color = Color.Black,
                            center = Offset(x, y),
                            radius = 6f
                        )

                        drawContext.canvas.nativeCanvas.drawText(
                            "$temp°C",
                            x,
                            y - 10f,
                            android.graphics.Paint().apply {
                                textAlign = android.graphics.Paint.Align.CENTER
                                this.textSize = textSize
                                color = textColor
                            }
                        )

                        if (index == 0) {
                            path.moveTo(x, y)
                        } else {
                            path.lineTo(x, y)
                        }
                    }

                    drawPath(
                        path = path,
                        color = Color.Black,
                        style = Stroke(width = 2f)
                    )
                }
            }

            // Day Labels using BoxWithConstraints to match dot positions
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.sizes.small)
            ) {
                val boxWidth = maxWidth
                temperatures.forEachIndexed { index, (day, _) ->
                    val barWidth = boxWidth / temperatures.size
                    val xOffset = barWidth * (index + 0.5f)

                    Text(
                        text = day,
                        style = AppTheme.typography.body2,
                        color = AppTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .offset(x = xOffset - (day.length.dp * 3))
                            .wrapContentSize(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}







