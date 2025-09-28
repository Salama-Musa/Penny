package com.salama.penny.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = DarkPink,
    onPrimary = WhiteText,
    secondary = BabyPink,
    onSecondary = BlackText
)

private val LightColors = lightColorScheme(
    primary = DarkPink,
    onPrimary = WhiteText,
    secondary = BabyPink,
    onSecondary = BlackText
)

@Composable
fun PennyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
