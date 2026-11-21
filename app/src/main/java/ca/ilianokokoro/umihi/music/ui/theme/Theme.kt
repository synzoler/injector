@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package ca.ilianokokoro.umihi.music.ui.theme

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun UmihiMusicTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        currentAccentIndex = context.getSharedPreferences("umihi_ui_prefs", Context.MODE_PRIVATE).getInt("accent_index", 0)
    }
    val accent = getAccentColor()
    val container = getAccentContainer()
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = container,
            onPrimary = TextPrimary,
            primaryContainer = accent,
            onPrimaryContainer = Color.White,
            background = Color(0xFF121318),
            onBackground = Color(0xFFE1E2E8),
            surface = Color(0xFF121318),
            onSurface = Color(0xFFE1E2E8),
            surfaceVariant = Color(0xFF1E2028),
            onSurfaceVariant = Color(0xFFC4C6D0)
        )
    } else {
        lightColorScheme(
            primary = accent,
            onPrimary = Color.White,
            primaryContainer = container,
            onPrimaryContainer = TextPrimary,
            background = AppBackground,
            onBackground = TextPrimary,
            surface = AppBackground,
            onSurface = TextPrimary,
            surfaceVariant = SurfaceCard,
            onSurfaceVariant = TextSecondary
        )
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        motionScheme = MotionScheme.expressive(),
        content = content
    )
}
