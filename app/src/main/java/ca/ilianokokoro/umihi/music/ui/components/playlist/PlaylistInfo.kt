package ca.ilianokokoro.umihi.music.ui.components.playlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ca.ilianokokoro.umihi.music.models.Playlist
import ca.ilianokokoro.umihi.music.ui.components.SquareImage
import ca.ilianokokoro.umihi.music.ui.icons.download
import ca.ilianokokoro.umihi.music.ui.icons.favorite
import ca.ilianokokoro.umihi.music.ui.theme.rememberStrings

@Composable
fun PlaylistInfo(
    playlist: Playlist,
    isDownloading: Boolean,
    onDownloadPressed: () -> Unit,
    onDeleteDownloadPressed: () -> Unit,
    onDeletePressed: () -> Unit,
    onRemoveFromLibraryPressed: () -> Unit,
    onCancelDownload: () -> Unit,
    onUnhidePlaylist: () -> Unit,
    onHidePlaylist: () -> Unit,
    onStartMix: () -> Unit,
    isLoading: Boolean = false,
    optionsExtended: Boolean = false,
    onOptionsExtendedChange: (Boolean) -> Unit = {}
) {
    val tr = rememberStrings()
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (playlist.info.id == "liked_songs") {
            Icon(
                imageVector = favorite,
                contentDescription = null,
                tint = Color(0xFF8B2E52),
                modifier = Modifier.size(120.dp)
            )
        } else if (!playlist.info.isDownloadedPlaylist) {
            SquareImage(uri = playlist.info.coverPath ?: playlist.info.coverHref)
        } else {
            Icon(
                imageVector = download,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = when (playlist.info.id) {
                    "liked_songs" -> tr.liked
                    else -> if (playlist.info.isDownloadedPlaylist) tr.downloads else playlist.info.title
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${playlist.songs.size} ${tr.songsCount}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
