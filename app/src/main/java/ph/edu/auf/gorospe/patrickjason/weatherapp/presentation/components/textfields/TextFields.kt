package ph.edu.auf.gorospe.patrickjason.weatherapp.presentation.components.textfields

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import ph.edu.auf.gorospe.patrickjason.weatherapp.ui.theme.AppTheme

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    textStyle: TextStyle = AppTheme.typography.body1,
    isError: Boolean = false,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier.padding(AppTheme.sizes.small),
        decorationBox = { innerTextField ->
            // Add styling and placeholder logic
            Box(
                modifier = Modifier
                    .padding(AppTheme.sizes.small)
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = AppTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        style = textStyle
                    )
                }
                innerTextField()
                trailingIcon?.invoke() ?: Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = AppTheme.colorScheme.onBackground,
                    modifier = Modifier.size(AppTheme.sizes.large)
                )
            }
        }
    )
}


//// Update the light theme version
//@Composable
//fun StyledTextFieldLight(
//    modifier: Modifier = Modifier,
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    enabled: Boolean = true,
//    leadingIcon: ImageVector? = null,
//    leadingIconContentDescription: String? = null,
//    trailingIcon: @Composable (() -> Unit)? = null
//) {
//    MaterialTheme(colorScheme = lightColorScheme()) {
//        StyledTextField(
//            modifier = modifier,
//            value = value,
//            onValueChange = onValueChange,
//            label = label,
//            enabled = enabled,
//            leadingIcon = leadingIcon,
//            leadingIconContentDescription = leadingIconContentDescription,
//            trailingIcon = trailingIcon
//        )
//    }
//}
//
//// Update the dark theme version
//@Composable
//fun StyledTextFieldDark(
//    modifier: Modifier = Modifier,
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    enabled: Boolean = true,
//    leadingIcon: ImageVector? = null,
//    leadingIconContentDescription: String? = null,
//    trailingIcon: @Composable (() -> Unit)? = null
//) {
//    AppTheme(isDarkTheme = true) {
//        StyledTextField(
//            modifier = modifier,
//            value = value,
//            onValueChange = onValueChange,
//            label = label,
//            enabled = enabled,
//            leadingIcon = leadingIcon,
//            leadingIconContentDescription = leadingIconContentDescription,
//            trailingIcon = trailingIcon
//        )
//    }
//}
//
