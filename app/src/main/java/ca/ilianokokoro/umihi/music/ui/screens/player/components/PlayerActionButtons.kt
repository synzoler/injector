package ca.ilianokokoro.umihi.music.ui.screens.player.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.ilianokokoro.umihi.music.ui.icons.*

@Composable
fun PlayerActionButtons(
    isLyricsShown: Boolean,
    isMixActive: Boolean,
    onToggleMix: () -> Unit,
    onOpenQueue: () -> Unit,
    onOpenVolume: () -> Unit,
    onOpenSleepTimer: () -> Unit,
    onToggleLyrics: () -> Unit,
    onOpenSpeedSelector: () -> Unit,
    playbackSpeed: Float,
    sleepTimerRemainingSeconds: Long?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalIconButton(
            onClick = onOpenVolume,
            shape = CircleShape,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(volume_up, contentDescription = null)
        }
        FilledTonalIconButton(
            onClick = onToggleLyrics,
            shape = CircleShape,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = if (isLyricsShown) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                contentColor = if (isLyricsShown) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(lyrics, contentDescription = null)
        }
        FilledTonalIconButton(
            onClick = onToggleMix,
            shape = CircleShape,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = if (isMixActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                contentColor = if (isMixActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(radio, contentDescription = null)
        }
        FilledTonalIconButton(
            onClick = onOpenQueue,
            shape = CircleShape,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(queue_music, contentDescription = null)
        }
        FilledTonalIconButton(
            onClick = onOpenSleepTimer,
            shape = CircleShape,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = if (sleepTimerRemainingSeconds != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                contentColor = if (sleepTimerRemainingSeconds != null) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(timer, contentDescription = null)
        }
        FilledTonalIconButton(
            onClick = onOpenSpeedSelector,
            shape = CircleShape,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = if (playbackSpeed != 1f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                contentColor = if (playbackSpeed != 1f) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(speed, contentDescription = null)
        }
    }
}
