package ph.edu.auf.gorospe.patrickjason.weatherapp.domain.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.main.MainScreen
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.screens.welcome.WelcomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.viewmodels.WelcomeViewModel
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.viewmodels.WelcomeViewModelFactory
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.providers.LocationProvider
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel: WelcomeViewModel = viewModel(
        factory = WelcomeViewModelFactory(
            locationProvider = LocationProvider(LocalContext.current)
        )
    )
    val latitude by viewModel.latitude.collectAsState()
    val longitude by viewModel.longitude.collectAsState()

    LaunchedEffect(latitude) {
        if (latitude.isNotEmpty()) {
            navController.navigate("main")
        }
    }

    NavHost(navController, startDestination = AppNavRoutes.WelcomeScreen.route) {
        composable(AppNavRoutes.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(
            "main/{latitude}/{longitude}",
            arguments = listOf(
                navArgument("latitude") { type = NavType.StringType },
                navArgument("longitude") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val latitude = backStackEntry.arguments?.getString("latitude") ?: ""
            val longitude = backStackEntry.arguments?.getString("longitude") ?: ""
            MainScreen(navController = navController, latitude = latitude, longitude = longitude)
        }
    }
}