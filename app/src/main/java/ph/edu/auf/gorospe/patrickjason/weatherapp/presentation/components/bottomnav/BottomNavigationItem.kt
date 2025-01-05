package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.components.bottomnav

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
    val badgeCount: Int? = null
)