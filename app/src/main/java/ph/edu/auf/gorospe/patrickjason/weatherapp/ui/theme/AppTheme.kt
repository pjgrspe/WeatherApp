package ph.edu.auf.gorospe.patrickjason.weatherapp.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val darkColorScheme = AppColorScheme(
    background = BackgroundDark,
    onBackground = GrayLight,
    primary = Primary,
    onPrimary = GrayLight,
    primaryDark = PrimaryDark,
    onPrimaryDark = GrayLight,
    secondary = Secondary,
    onSecondary = GrayDarker,
    accent = Accent,
    onAccent = GrayLight,
    error = ErrorColor,
    onError = GrayLight,
    success = SuccessColor,
    onSuccess = GrayLight
)

private val lightColorScheme = AppColorScheme(
    background = BackgroundLight,
    onBackground = GrayDarker,
    primary = Primary,
    onPrimary = GrayLight,
    primaryDark = PrimaryDark,
    onPrimaryDark = GrayLight,
    secondary = Secondary,
    onSecondary = GrayDarker,
    accent = Accent,
    onAccent = GrayLight,
    error = ErrorColor,
    onError = GrayLight,
    success = SuccessColor,
    onSuccess = GrayLight
)

private val typography = AppTypography(
    h1 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    h2 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    h3 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    h4 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 22.sp
    ),
    h5 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    h6 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    body1 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    body2 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    button = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    caption = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)

private val shapes = AppShape(
    container = RoundedCornerShape(24.dp),  // Very rounded containers
    button = RoundedCornerShape(50.dp),    // Almost pill-shaped buttons
    textField = RoundedCornerShape(16.dp),  // Very rounded text fields
    card = RoundedCornerShape(24.dp)      // Very rounded cards
)

private val sizes = AppSize(
    tiny = 4.dp,
    small = 8.dp,
    normal = 12.dp,
    medium = 16.dp,
    large = 24.dp,
    xLarge = 32.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shapes,
        LocalAppSize provides sizes,
        LocalIndication provides rippleIndication,
        content = content
    )
}

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable
        get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current

    val shapes: AppShape
        @Composable
        get() = LocalAppShape.current

    val sizes: AppSize
        @Composable
        get() = LocalAppSize.current
}