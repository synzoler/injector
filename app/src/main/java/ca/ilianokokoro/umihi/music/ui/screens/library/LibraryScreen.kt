package ca.ilianokokoro.umihi.music.ui.screens.library

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.painterResource
import ca.ilianokokoro.umihi.music.R
import ca.ilianokokoro.umihi.music.core.Constants
import ca.ilianokokoro.umihi.music.models.PlaylistInfo
import ca.ilianokokoro.umihi.music.ui.screens.home.HomeViewModel
import ca.ilianokokoro.umihi.music.ui.screens.home.ScreenState

@Composable
fun LibraryScreen(
    onPlaylistPressed: (PlaylistInfo) -> Unit,
    application: Application,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(application))
) {
    val tr = ca.ilianokokoro.umihi.music.ui.theme.rememberStrings()
    val uiState by viewModel.uiState.collectAsState()
    var selectedFilter by remember { mutableIntStateOf(0) }
    val filters = listOf(tr.all, tr.downloads, tr.playlists)

    val allPlaylists = when (val state = uiState.screenState) {
        is ScreenState.LoggedIn -> state.playlistInfos
        else -> emptyList()
    }

    val downloadedPlaylist = allPlaylists.find { it.id == Constants.Downloads.DOWNLOADED_PLAYLIST_ID }
        ?: PlaylistInfo(
            id = Constants.Downloads.DOWNLOADED_PLAYLIST_ID,
            title = "Descargas",
            ).apply { songCount = uiState.downloadedSongsCount }

    val likedCount = uiState.likedSongsCount
    val likedPlaylist = allPlaylists.find { it.id == "liked_songs" }
        ?: PlaylistInfo(
            id = "liked_songs",
            title = "Me gusta"
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = tr.library,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            filters.forEachIndexed { index, label ->
                FilterChip(
                    selected = selectedFilter == index,
                    onClick = { selectedFilter = index },
                    label = { Text(label) },
                    shape = CircleShape
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (selectedFilter == 0 || selectedFilter == 1) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(145.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .clickable { onPlaylistPressed(downloadedPlaylist) },
                    color = Color(0xFFDCE6FA)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFBACDF5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(painterResource(R.drawable.download), contentDescription = null, tint = Color(0xFF1F3A60))
                        }
                        Column {
                            Text(tr.downloads, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1C1B1F))
                            Text("${uiState.downloadedSongsCount} ${tr.songsCount}", fontSize = 13.sp, color = Color(0xFF49454F))
                        }
                    }
                }

                if (selectedFilter == 0) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(145.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .clickable { onPlaylistPressed(likedPlaylist) },
                        color = Color(0xFFFADCE6)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF3B8CC)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(painterResource(R.drawable.play), contentDescription = null, tint = Color(0xFF6E1E3B))
                            }
                            Column {
                                Text(tr.liked, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1C1B1F))
                                Text("${likedPlaylist.songCount ?: 0} ${tr.songsCount}", fontSize = 13.sp, color = Color(0xFF49454F))
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(22.dp))
        }

        Text(
            text = tr.yourPlaylists,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val visiblePlaylists = when (selectedFilter) {
            1 -> listOf(downloadedPlaylist)
            2 -> allPlaylists.filter { it.id != Constants.Downloads.DOWNLOADED_PLAYLIST_ID }
            else -> allPlaylists
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(visiblePlaylists) { playlist ->
                val isLiked = playlist.id == "liked_songs"
                val isDownloaded = playlist.id == Constants.Downloads.DOWNLOADED_PLAYLIST_ID
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onPlaylistPressed(playlist) },
                    color = if (isLiked) Color(0xFFFADCE6) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = when {
                                isLiked -> painterResource(R.drawable.play)
                                isDownloaded -> painterResource(R.drawable.download)
                                else -> painterResource(R.drawable.shuffle)
                            },
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = if (isLiked) Color(0xFF6E1E3B) else MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isLiked) "Me gusta" else playlist.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "${playlist.songCount ?: 0} ${tr.songsCount}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(painterResource(R.drawable.play), contentDescription = null)
                    }
                }
            }
        }
    }
}
