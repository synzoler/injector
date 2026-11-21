package ca.ilianokokoro.umihi.music.ui.screens.search

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.painterResource
import ca.ilianokokoro.umihi.music.R
import ca.ilianokokoro.umihi.music.core.managers.PlayerManager
import ca.ilianokokoro.umihi.music.models.Song

@Composable
fun SearchScreen(
    application: Application,
    initialQuery: String = "",
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory(application))
) {
    val tr = ca.ilianokokoro.umihi.music.ui.theme.rememberStrings()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(initialQuery) {
        if (initialQuery.isNotBlank() && uiState.search.isBlank()) {
            viewModel.onSearchFieldChange(initialQuery)
            viewModel.search()
        }
    }

    val genres = listOf(
        tr.genres[0] to Color(0xFFDCE6FA),
        tr.genres[1] to Color(0xFFFADCE6),
        tr.genres[2] to Color(0xFFFAECD2),
        tr.genres[3] to Color(0xFFAFE9DD),
        tr.genres[4] to Color(0xFFE8DDF8),
        tr.genres[5] to Color(0xFFD4F0F7),
        tr.genres[6] to Color(0xFFFCE2D4),
        tr.genres[7] to Color(0xFFE6F3D4)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        OutlinedTextField(
            value = uiState.search,
            onValueChange = { viewModel.onSearchFieldChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(tr.searchShort) },
            leadingIcon = { Icon(painterResource(R.drawable.play), contentDescription = null) },
            trailingIcon = {
                if (uiState.search.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onSearchFieldChange("") }) {
                        Icon(painterResource(R.drawable.shuffle), contentDescription = null)
                    }
                }
            },
            shape = RoundedCornerShape(28.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { viewModel.search() })
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (uiState.search.isBlank()) {
            Text(
                text = tr.exploreByGenre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 14.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(genres) { (name, color) ->
                    Box(
                        modifier = Modifier
                            .height(105.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(color)
                            .clickable {
                                viewModel.onSearchFieldChange(name)
                                viewModel.search()
                            }
                            .padding(16.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(
                            text = name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1C1B1F)
                        )
                    }
                }
            }
        } else {
            when (val state = uiState.screenState) {
                is ScreenState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is ScreenState.Error -> {
                    Text("Error al buscar: ${state.exception.message}")
                }
                is ScreenState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.results) { song ->
                            SongSearchRow(song = song, onClick = {
                                PlayerManager.playMix(song)
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SongSearchRow(song: Song, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(song.title, fontWeight = FontWeight.SemiBold, maxLines = 1)
            Text(song.artists, style = MaterialTheme.typography.bodySmall, maxLines = 1)
        }
    }
}
