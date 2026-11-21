package ca.ilianokokoro.umihi.music.ui.screens.player.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ilianokokoro.umihi.music.models.PlaybackAudioInfo

@Composable
fun PlaybackAudioInfoPill(
    info: PlaybackAudioInfo,
    modifier: Modifier = Modifier,
) {
    val rawLabel = info.getDisplayLabel(LocalResources.current)
    val lastLabel = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("48.0 kHz • OPUS") }
    if (rawLabel.isNotBlank()) lastLabel.value = rawLabel
    val label = lastLabel.value
    val hasLabel = true

    Surface(
        modifier = modifier.height(24.dp).alpha(
            if (hasLabel) {
                1f
            } else {
                0f
            }
        ),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
        )
    }
}