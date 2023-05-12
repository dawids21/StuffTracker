package xyz.stasiak.stufftracker.ui.product

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductImage(
    imageUri: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    imageUri?.let {
        Image(
            painter = rememberAsyncImagePainter(it.toUri()),
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
        )
    } ?: Icon(
        Icons.Filled.Inventory2,
        contentDescription = null,
//        contentScale = contentScale,
        modifier = modifier,
    )
}