package com.example.connectio.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Indigo, // Kolor kwadratów na planszy
    secondary = OffRed,
    primaryContainer = Indigo, //Kolor pozostałych pojemników UI
    secondaryContainer = Tangerine, // Kolor kwadratów na górnym i dolnym pasku
    background = BlueGray, // kolor tła
    onBackground = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = PapayaWhip, // Kolor kwadratów na planszy
    secondary = LightGreen,
    primaryContainer = DarkGreen, //Kolor pozostałych pojemników UI
    secondaryContainer = PapayaWhip,
    background = LightGreen,
    onBackground = Color.White,
)

@Composable
fun ConnectioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}