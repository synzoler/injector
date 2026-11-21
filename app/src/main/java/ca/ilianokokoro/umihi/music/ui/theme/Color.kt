package ca.ilianokokoro.umihi.music.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val AppBackground = Color(0xFFF6F7FB)
val SurfaceCard = Color(0xFFECEEF4)
val TextPrimary = Color(0xFF1A1C1E)
val TextSecondary = Color(0xFF44474F)

val PastelBlueBg = Color(0xFFD6E4FF)
val PastelBlueAccent = Color(0xFF3B5BA9)

val PastelMintBg = Color(0xFFD0F0E4)
val PastelMintAccent = Color(0xFFAFE9DD)

val PastelLilacBg = Color(0xFFEADDFF)
val PastelLilacAccent = Color(0xFF5A3D9A)

val PastelPinkBg = Color(0xFFFAD8E6)
val PastelPinkAccent = Color(0xFF983663)

val PastelCreamBg = Color(0xFFF7E8C8)
val PastelCreamAccent = Color(0xFF7A590C)

val ActiveBlueToggle = Color(0xFF3E5AA9)
val ActivePurpleToggle = Color(0xFF56479C)
val InactiveToggleBg = Color(0xFFE5E7EF)

var currentAccentIndex by mutableIntStateOf(0)
fun getAccentColor(): Color = when (currentAccentIndex) {
    0 -> PastelMintAccent
    1 -> PastelBlueAccent
    2 -> PastelCreamAccent
    3 -> PastelPinkAccent
    4 -> PastelLilacAccent
    else -> PastelMintAccent
}
fun getAccentContainer(): Color = when (currentAccentIndex) {
    0 -> PastelMintBg
    1 -> PastelBlueBg
    2 -> PastelCreamBg
    3 -> PastelPinkBg
    4 -> PastelLilacBg
    else -> PastelMintBg
}
