package ca.ilianokokoro.umihi.music.ui.components.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import ca.ilianokokoro.umihi.music.data.repositories.SongRepository
import ca.ilianokokoro.umihi.music.core.ApiResult
import androidx.compose.ui.platform.LocalContext
import android.app.Application
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

data class ItunesTrack(val title: String, val artist: String, val album: String, val artwork: String)
data class ItunesAlbum(val title: String, val artwork: String, val year: String)

private fun splitArtists(raw: String): List<String> {
    val parts = raw.split(Regex("\\s*(?:&|,|\\bfeat\\.?\\b|\\bft\\.?\\b|\\bx\\b|\\bwith\\b)\\s*", RegexOption.IGNORE_CASE))
        .map { it.trim() }.filter { it.isNotBlank() }.distinct()
    return if (parts.isEmpty()) listOf(raw.trim()) else parts
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistChannelSheet(
    artistName: String,
    onDismiss: () -> Unit,
    onPlayTrack: (String) -> Unit
) {
    val context = LocalContext.current
    val artistList = remember(artistName) { splitArtists(artistName) }
    var selectedArtist by remember(artistName) { mutableStateOf(artistList.first()) }
    var tracks by remember { mutableStateOf<List<ItunesTrack>>(emptyList()) }
    var albums by remember { mutableStateOf<List<ItunesAlbum>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(selectedArtist) {
        isLoading = true
        var loadedTracks = emptyList<ItunesTrack>()
        var loadedAlbums = emptyList<ItunesAlbum>()
        withContext(Dispatchers.IO) {
            runCatching {
                val q = URLEncoder.encode(selectedArtist, "UTF-8")
                val conn = URL("https://itunes.://apple.com?term=$q&entity=song&country=US&limit=25").openConnection() as HttpURLConnection
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)")
                conn.connectTimeout = 7000
                conn.readTimeout = 7000
                val json = JSONObject(conn.inputStream.bufferedReader().readText())
                val arr = json.getJSONArray("results")
                val tList = mutableListOf<ItunesTrack>()
                val aMap = linkedMapOf<String, ItunesAlbum>()
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val art = o.optString("artistName", selectedArtist)
                    val track = o.optString("trackName", "")
                    val col = o.optString("collectionName", "")
                    val img = o.optString("artworkUrl100", "").replace("100x100bb", "600x600bb")
                    val year = o.optString("releaseDate", "").take(4)
                    if (track.isNotBlank()) tList.add(ItunesTrack(track, art, col, img))
                    if (col.isNotBlank() && !aMap.containsKey(col)) aMap[col] = ItunesAlbum(col, img, year)
                }
                loadedTracks = tList
                loadedAlbums = aMap.values.toList()
            }
        }
        if (loadedTracks.isEmpty()) {
            val app = context.applicationContext as? Application
            if (app != null) {
                runCatching {
                    SongRepository(app).search(selectedArtist).collect { res ->
                        if (res is ApiResult.Success) {
                            loadedTracks = res.data.map { s ->
                                ItunesTrack(s.title, s.artists.ifBlank { artistName }, s.duration, s.thumbnailHref)
                            }
                            loadedAlbums = res.data.take(6).map { s ->
                                ItunesAlbum(s.title, s.thumbnailHref, "")
                            }
                        }
                    }
                }
            }
        }
        tracks = loadedTracks
        albums = loadedAlbums
        isLoading = false
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            if (artistList.size > 1) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                    items(artistList) { art ->
                        FilterChip(selected = art == selectedArtist, onClick = { selectedArtist = art }, label = { Text(art, maxLines = 1) })
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AsyncImage(
                    model = tracks.firstOrNull()?.artwork,
                    contentDescription = selectedArtist,
                    modifier = Modifier.size(64.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(selectedArtist, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Canal oficial • ${albums.size} álbumes • ${tracks.size} canciones", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 460.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (albums.isNotEmpty()) {
                        item {
                            Text("Álbumes", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(albums) { alb ->
                                    Column(modifier = Modifier.width(120.dp).clickable { onPlayTrack("${alb.title} $artistName") }) {
                                        AsyncImage(model = alb.artwork, contentDescription = alb.title, modifier = Modifier.size(120.dp).clip(RoundedCornerShape(14.dp)), contentScale = ContentScale.Crop)
                                        Text(alb.title, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                        if (alb.year.isNotBlank()) Text(alb.year, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }
                    item { Text("Canciones / Singles", fontWeight = FontWeight.Bold, fontSize = 17.sp) }
                    items(tracks) { trk ->
                        Row(
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).clickable { onPlayTrack("${trk.title} ${trk.artist}") }.padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AsyncImage(model = trk.artwork, contentDescription = trk.title, modifier = Modifier.size(48.dp).clip(RoundedCornerShape(10.dp)), contentScale = ContentScale.Crop)
                            Column(modifier = Modifier.weight(1f)) {
                                Text(trk.title, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.SemiBold)
                                Text(trk.album, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
