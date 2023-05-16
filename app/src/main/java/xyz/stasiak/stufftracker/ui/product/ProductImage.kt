package xyz.stasiak.stufftracker.ui.product

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.core.net.toUri
import coil.compose.AsyncImage

@Composable
fun ProductImage(
    imageUri: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    var loaded by remember { mutableStateOf(false) }
    AsyncImage(
        model = imageUri?.toUri(),
        contentDescription = null,
        contentScale = contentScale,
        fallback = rememberVectorPainter(image = Icons.Filled.Inventory2),
        placeholder = rememberVectorPainter(image = Icons.Filled.Inventory2),
        error = rememberVectorPainter(image = Icons.Filled.Inventory2),
        onSuccess = { loaded = true },
        modifier = modifier,
        colorFilter = if (!loaded) ColorFilter.tint(LocalContentColor.current) else null
    )
}