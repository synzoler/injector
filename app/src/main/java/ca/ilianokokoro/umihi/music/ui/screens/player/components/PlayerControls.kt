package ca.ilianokokoro.umihi.music.ui.screens.player.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.PI
import kotlin.math.sin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.ilianokokoro.umihi.music.R
import ca.ilianokokoro.umihi.music.core.helpers.ComposeHelper
import ca.ilianokokoro.umihi.music.core.managers.PlayerManager
import ca.ilianokokoro.umihi.music.extensions.toTimeString
import ca.ilianokokoro.umihi.music.ui.icons.pause
import ca.ilianokokoro.umihi.music.ui.icons.play_arrow
import ca.ilianokokoro.umihi.music.ui.icons.skip_next
import ca.ilianokokoro.umihi.music.ui.icons.skip_previous
import ca.ilianokokoro.umihi.music.ui.screens.player.PlaybackProgress
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    isLoading: Boolean,
    progress: StateFlow<PlaybackProgress>,
    onSeekPlayer: () -> Unit,
    onUpdateSeekBarHeldState: (isHeld: Boolean) -> Unit,
    onSeek: (location: Float) -> Unit,
) {
    val mainButtonsControlsInteractionSources =
        List(3) { ComposeHelper.rememberInteractionSource() }

    val hapticFeedback = LocalHapticFeedback.current

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SeekBar(
            progress = progress,
            onSeek = onSeek,
            onSeekPlayer = onSeekPlayer,
            onUpdateSeekBarHeldState = onUpdateSeekBarHeldState,
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonGroup(
                overflowIndicator = {},
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                customItem(
                    {
                        FilledIconButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                                PlayerManager.skipToPrevious()
                            },
                            shapes = IconButtonDefaults.shapes(),
                            interactionSource = mainButtonsControlsInteractionSources[0],
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                                .weight(2f)
                                .size(IconButtonDefaults.mediumContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide))
                                .animateWidth(interactionSource = mainButtonsControlsInteractionSources[0])
                        ) {
                            Icon(
                                imageVector = skip_previous,
                                contentDescription = stringResource(R.string.previous),
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    },
                    {}
                )

                customItem(
                    {

                        FilledIconToggleButton(
                            checked = isPlaying,
                            onCheckedChange = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                                if (isPlaying) {
                                    PlayerManager.currentController?.pause()
                                } else {
                                    PlayerManager.currentController?.play()
                                }
                            },
                            shapes = IconButtonDefaults.toggleableShapes(),
                            colors = IconButtonDefaults.filledIconToggleButtonColors(
                                checkedContainerColor = IconButtonDefaults.filledIconToggleButtonColors().checkedContainerColor,
                                checkedContentColor = IconButtonDefaults.filledIconToggleButtonColors().checkedContentColor,
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            interactionSource = mainButtonsControlsInteractionSources[1],
                            modifier = Modifier
                                .weight(3f)
                                .size(IconButtonDefaults.largeContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide))
                                .animateWidth(interactionSource = mainButtonsControlsInteractionSources[1])
                        ) {
                            if (false) {
                                CircularWavyProgressIndicator(
                                    modifier = Modifier.size(25.dp),
                                )
                            } else {
                                val icon = if (isPlaying) {
                                    pause
                                } else {
                                    play_arrow
                                }

                                val text = if (isPlaying) {
                                    R.string.pause
                                } else {
                                    R.string.play
                                }

                                Icon(
                                    imageVector = icon,
                                    contentDescription = stringResource(text),
                                    modifier = Modifier.size(50.dp)
                                )
                            }


                        }
                    },
                    {}
                )

                customItem(
                    {
                        FilledIconButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                                PlayerManager.skipToNext()
                            },
                            shapes = IconButtonDefaults.shapes(),
                            interactionSource = mainButtonsControlsInteractionSources[2],
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                                .weight(2f)
                                .size(IconButtonDefaults.mediumContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide))
                                .animateWidth(interactionSource = mainButtonsControlsInteractionSources[2])
                        ) {
                            Icon(
                                imageVector = skip_next,
                                contentDescription = stringResource(R.string.next),
                                modifier = Modifier.size(30.dp)

                            )
                        }
                    },
                    {}
                )
            }
        }
    }
}


@Composable
private fun SeekBar(
    progress: StateFlow<PlaybackProgress>,
    onSeek: (Float) -> Unit,
    onSeekPlayer: () -> Unit,
    onUpdateSeekBarHeldState: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val progressValue by progress.collectAsStateWithLifecycle()
    val audioInfo by PlayerManager.audioInfo.collectAsStateWithLifecycle()
    val duration = progressValue.duration.coerceAtLeast(1f)
    val fraction = (progressValue.position / duration).coerceIn(0f, 1f)
    val inf = rememberInfiniteTransition(label = "wave")
    val phase by inf.animateFloat(0f, (2f * PI).toFloat(), infiniteRepeatable(tween(1800, easing = LinearEasing)), label = "p")
    val accent = MaterialTheme.colorScheme.primary
    val trackBg = accent.copy(alpha = 0.22f)
    Column(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxWidth().height(34.dp).pointerInput(duration) { detectTapGestures { off -> val f = (off.x / size.width.coerceAtLeast(1)).coerceIn(0f, 1f); onUpdateSeekBarHeldState(true); onSeek(f * duration); onSeekPlayer(); onUpdateSeekBarHeldState(false) } }.pointerInput(duration) { detectHorizontalDragGestures(onDragStart = { onUpdateSeekBarHeldState(true) }, onDragEnd = { onSeekPlayer(); onUpdateSeekBarHeldState(false) }, onDragCancel = { onUpdateSeekBarHeldState(false) }) { change, _ -> val f = (change.position.x / size.width.coerceAtLeast(1)).coerceIn(0f, 1f); onSeek(f * duration) } }) {
            val midY = size.height / 2f
            val activeW = size.width * fraction
            drawLine(trackBg, Offset(activeW, midY), Offset(size.width, midY), strokeWidth = 4.dp.toPx(), cap = StrokeCap.Round)
            val path = Path()
            val amp = 4.5.dp.toPx()
            val freq = 0.055f
            var x = 0f
            while (x <= activeW) { val y = midY + sin(x * freq + phase) * amp; if (x == 0f) path.moveTo(x, y) else path.lineTo(x, y); x += 3f }
            drawPath(path, accent, style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round))
            drawCircle(accent, radius = 6.dp.toPx(), center = Offset(activeW, midY))
        }
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.Center), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = progressValue.position.toTimeString(), style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = progressValue.duration.toTimeString(), style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Box(modifier = Modifier.height(34.dp).align(Alignment.Center).padding(horizontal = 58.dp), contentAlignment = Alignment.Center) { PlaybackAudioInfoPill(info = audioInfo) }
        }
    }
}
