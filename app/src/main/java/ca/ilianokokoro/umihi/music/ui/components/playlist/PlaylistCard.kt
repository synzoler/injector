package ca.ilianokokoro.umihi.music.ui.components.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ilianokokoro.umihi.music.R
import ca.ilianokokoro.umihi.music.models.PlaylistInfo
import ca.ilianokokoro.umihi.music.ui.components.SquareImage
import ca.ilianokokoro.umihi.music.ui.icons.play_arrow
import ca.ilianokokoro.umihi.music.ui.theme.*

@Composable
fun PlaylistCard(
    playlistInfo: PlaylistInfo,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    songCount: Int? = playlistInfo.songCount,
) {
    Row(
        modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(SurfaceCard).clickable { onClicked() }.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(Modifier.size(60.dp).clip(RoundedCornerShape(16.dp))) {
            if (!playlistInfo.isDownloadedPlaylist) {
                SquareImage(uri = playlistInfo.coverPath ?: playlistInfo.coverHref, contentDescription = stringResource(R.string.playlist_cover))
            } else {
                OfflineThumbnail()
            }
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = playlistInfo.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            val countText = songCount?.let { stringResource(R.string.songs, it) } ?: "Playlist"
            Box(Modifier.clip(CircleShape).background(PastelBlueBg).padding(horizontal = 10.dp, vertical = 3.dp)) {
                Text(text = countText, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = PastelBlueAccent)
            }
        }
        Box(Modifier.size(38.dp).clip(CircleShape).background(PastelLilacBg), contentAlignment = Alignment.Center) {
            Icon(play_arrow, "Play", tint = PastelLilacAccent, modifier = Modifier.size(20.dp))
        }
    }
}
