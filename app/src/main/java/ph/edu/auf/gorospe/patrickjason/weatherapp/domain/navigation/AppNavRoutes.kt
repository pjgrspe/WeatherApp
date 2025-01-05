package ph.edu.auf.gorospe.patrickjason.weatherapp.domain.navigation

sealed class AppNavRoutes(val route: String) {
    object WelcomeScreen : AppNavRoutes("welcome")
    object MainScreen : AppNavRoutes("main")
    object PetList : AppNavRoutes("pet_list")
    object OwnerList : AppNavRoutes("owner_list")
}