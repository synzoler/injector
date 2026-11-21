package ca.ilianokokoro.umihi.music.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ca.ilianokokoro.umihi.music.core.cache.CoilImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun SquareImage(
    modifier: Modifier = Modifier,
    uri: String,
    contentDescription: String? = null,
    cornerRadius: Dp = 12.dp,
) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(uri)
            .crossfade(220)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(cornerRadius)),
        imageLoader = CoilImageLoader.get(context),
    )
}
