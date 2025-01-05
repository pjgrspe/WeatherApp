package ph.edu.auf.gorospe.patrickjason.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import io.realm.kotlin.Realm
import ph.edu.auf.gorospe.patrickjason.weatherapp.domain.navigation.AppNavigation
import ph.edu.auf.gorospe.patrickjason.weatherapp.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
//                SetBarColor(color = AppTheme.colorScheme.background)
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
    //SET ANDROID STATUS BAR COLOR
//    private fun SetBarColor(color: Color){
//        val systemUIController = rememberSystemUiController()
//        SideEffect {
//            systemUIController.setSystemBarsColor(color = color)
//        }
//    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}