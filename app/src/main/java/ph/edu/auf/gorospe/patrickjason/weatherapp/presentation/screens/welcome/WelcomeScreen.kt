package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.welcome

import android.Manifest
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import ph.edu.auf.gorospe.patrickjason.weatherapp.R
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.components.buttons.PrimaryButton
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.viewmodels.WelcomeViewModelFactory
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.providers.LocationProvider
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.components.buttons.SecondaryButton
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.viewmodels.WelcomeViewModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.ui.theme.AppTheme
import ph.edu.auf.gorospe.patrickjason.weatherapp.ui.theme.Secondary

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeViewModel = viewModel(
        factory = WelcomeViewModelFactory(
            locationProvider = LocationProvider(LocalContext.current)
        )
    )
) {
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            Log.d("WelcomeScreen", "Location permission granted")
            viewModel.getCurrentLocation()
        }
    }
    val latitude by viewModel.latitude.collectAsState()
    val longitude by viewModel.longitude.collectAsState()
    var showPermissionRequest by remember { mutableStateOf(false) }

    val backgroundColor = AppTheme.colorScheme.background
    val textColor = AppTheme.colorScheme.onBackground

    LaunchedEffect(latitude, longitude) {
        if (latitude.isNotEmpty() && longitude.isNotEmpty()) {
            navController.navigate("main/$latitude/$longitude")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor) // Set background color
    ) {
        if (showPermissionRequest && !locationPermissionState.status.isGranted) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Location permission is required to use this app.",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = textColor // Set text color
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (locationPermissionState.status.shouldShowRationale) {
                    Text(
                        text = "Please grant the permission to access your location.",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = textColor // Set text color
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                SecondaryButton(
                    label = "Request Permission",
                    onClick = { locationPermissionState.launchPermissionRequest() }
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to the Weather App",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = textColor, // Set text color
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                SecondaryButton(
                    label = "Get Started",
                    onClick = {
                        showPermissionRequest = true
                        if (locationPermissionState.status.isGranted) {
                            viewModel.getCurrentLocation()
                        } else {
                            locationPermissionState.launchPermissionRequest()
                        }
                    }
                )
            }
        }
    }
}
