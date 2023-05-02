package xyz.stasiak.stufftracker.ui.item.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import xyz.stasiak.stufftracker.data.Item

@Composable
fun ItemDetailsBodyContent(item: Item) {
    Image(
        painter = painterResource(item.image),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
    ItemHeader(item)
    ItemParameters(item = item)
}