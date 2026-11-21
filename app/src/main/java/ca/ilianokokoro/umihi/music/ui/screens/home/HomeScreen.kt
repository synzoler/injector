package ca.ilianokokoro.umihi.music.ui.screens.home

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import ca.ilianokokoro.umihi.music.core.ApiResult
import ca.ilianokokoro.umihi.music.core.managers.PlayerManager
import ca.ilianokokoro.umihi.music.data.repositories.SongRepository
import ca.ilianokokoro.umihi.music.models.PlaylistInfo
import ca.ilianokokoro.umihi.music.models.Song
import androidx.compose.ui.res.painterResource
import ca.ilianokokoro.umihi.music.R
import ca.ilianokokoro.umihi.music.ui.navigation.viewmodels.SharedViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

private data class MixCardData(
    val title: String,
    val subtitle: String,
    val query: String,
    val cardColor: Color,
    val iconBgColor: Color,
    val buttonColor: Color
)

@Composable
fun HomeScreen(
    onSearchPressed: () -> Unit,
    sharedViewModel: SharedViewModel,
    onPlaylistPressed: (PlaylistInfo) -> Unit,
    onLoginPressed: () -> Unit,
    application: Application
) {
    val tr = ca.ilianokokoro.umihi.music.ui.theme.rememberStrings()
    val scope = rememberCoroutineScope()
    val songRepository = remember { SongRepository(application) }
    val chips = listOf(tr.forYou, tr.albums, tr.artists, tr.energy)
    var selectedChip by remember { mutableIntStateOf(0) }
    var chipSongs by remember { mutableStateOf<List<Song>>(emptyList()) }
    var isLoadingChip by remember { mutableStateOf(false) }

    val mixCards = listOf(
        MixCardData(tr.mix1Title, tr.mix1Sub, "top hits mix", Color(0xFFDCE6FA), Color(0xFFC4D6F6), Color(0xFF284677)),
        MixCardData(tr.mix2Title, tr.mix2Sub, "pop electronic indie mix", Color(0xFFD6F2E1), Color(0xFFBBE6CC), Color(0xFF1D5C3B)),
        MixCardData(tr.mix3Title, tr.mix3Sub, "new albums hits", Color(0xFFE8DDF8), Color(0xFFD6C3F2), Color(0xFF4F2B82)),
        MixCardData(tr.mix4Title, tr.mix4Sub, "trending artists radio mix", Color(0xFFFADCE6), Color(0xFFF4C1D2), Color(0xFF7D2446)),
        MixCardData(tr.mix5Title, tr.mix5Sub, "dance workout energy mix", Color(0xFFFAECD2), Color(0xFFF3DCB0), Color(0xFF735314))
    )

    fun loadTopicSongs(topic: String) {
        scope.launch {
            isLoadingChip = true
            val q = when (topic) {
                "Álbumes" -> "full album top songs"
                "Artistas" -> "top artists popular songs"
                "Energía" -> "energetic dance workout music"
                else -> "recommended music mix"
            }
            val res = songRepository.search(q).firstOrNull { it is ApiResult.Success || it is ApiResult.Error }
            if (res is ApiResult.Success) chipSongs = res.data.shuffled()
            isLoadingChip = false
        }
    }

    fun playRandomMix(query: String) {
        scope.launch {
            val res = songRepository.search(query).firstOrNull { it is ApiResult.Success || it is ApiResult.Error }
            if (res is ApiResult.Success && res.data.isNotEmpty()) {
                PlayerManager.playMix(res.data.shuffled().first())
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Music", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Surface(
                modifier = Modifier.weight(1f).height(46.dp).clip(CircleShape).clickable { onSearchPressed() },
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
                Row(modifier = Modifier.padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(R.drawable.play), contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.width(8.dp))
                    Text(tr.searchPlaceholder, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                }
            }
        }
        Spacer(Modifier.height(14.dp))
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            chips.forEachIndexed { index, label ->
                FilterChip(
                    selected = selectedChip == index,
                    onClick = { selectedChip = index; if (index > 0) loadTopicSongs(label) },
                    label = { Text(label) },
                    shape = CircleShape
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        if (selectedChip == 0) {
            Text(tr.recommendationsForYou, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize()) {
                items(mixCards) { card ->
                    Surface(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).clickable { playRandomMix(card.query) },
                        color = card.cardColor
                    ) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(54.dp).clip(RoundedCornerShape(16.dp)).background(card.iconBgColor), contentAlignment = Alignment.Center) {
                                Icon(painterResource(R.drawable.shuffle), contentDescription = null, tint = card.buttonColor)
                            }
                            Spacer(Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(card.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1C1B1F))
                                Text(card.subtitle, fontSize = 13.sp, color = Color(0xFF49454F))
                            }
                            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(card.buttonColor), contentAlignment = Alignment.Center) {
                                Icon(painterResource(R.drawable.play), contentDescription = null, tint = Color.White)
                            }
                        }
                    }
                }
            }
        } else {
            Text("Canciones relacionadas: ${chips[selectedChip]}", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
            if (isLoadingChip) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
                    items(chipSongs) { song ->
                        Surface(
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).clickable { PlayerManager.playMix(song) },
                            tonalElevation = 1.dp
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(song.title, fontWeight = FontWeight.SemiBold, maxLines = 1)
                                    Text(song.artists, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                                }
                                Icon(painterResource(R.drawable.play), contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}
