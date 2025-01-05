package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.components.bottomnav

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.components.bottomnav.BottomNavigationItem
import ph.edu.auf.gorospe.patrickjason.weatherapp.ui.theme.AppTheme

//ITEMS
val items = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        badgeCount = null
    ),
    BottomNavigationItem(
        title = "Saved",
        selectedIcon = Icons.Filled.LocationOn,
        unselectedIcon = Icons.Outlined.LocationOn,
        badgeCount = null
    ),
    BottomNavigationItem(
        title = "Alerts",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        badgeCount = 2
    )
)

//BOTTOM NAVIGATION BAR
@Preview(showBackground = true)
@Composable
fun BottomNavigationBar() {
    //Tracks the currently selected item in the bottom navigation bar
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    //Main navigation bar with rounded corners and elevated background
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .height(90.dp),
        containerColor = AppTheme.colorScheme.background,
        tonalElevation = AppTheme.sizes.small
    ) {
        items.forEachIndexed { index, item ->
            //check if item is selected
            val isSelected = selectedItemIndex == index

            //icon color based on selection state
            val iconColor = if (isSelected) AppTheme.colorScheme.primary else AppTheme.colorScheme.onBackground

            //bounce animation when selected
            val scale = animateFloatAsState(if (isSelected) 1.2f else 1f, label = "")

            //column for each item
            Column(
                modifier = Modifier
                    .weight(1f)  //Distribute items equally in the navigation bar
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            selectedItemIndex = index  //Update selected item on tap
                        })
                    }
                    .padding(vertical = AppTheme.sizes.small),  //Add vertical padding to each item
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //Badge box to display an optional badge on top of the icon
                BadgedBox(badge = {
                    item.badgeCount?.let {
                        Badge { Text(text = it.toString()) }
                    }
                }) {
                    //Icon with a scaling effect for the bounce animation
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = iconColor,
                        modifier = Modifier
                            .size(AppTheme.sizes.large)
                            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)  // Apply bounce scale
                    )
                }

                Spacer(modifier = Modifier.height(AppTheme.sizes.medium))  // Space between icon and text

                //Display the title of the item, with bold font if selected
                Text(
                    text = item.title,
                    color = iconColor,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

