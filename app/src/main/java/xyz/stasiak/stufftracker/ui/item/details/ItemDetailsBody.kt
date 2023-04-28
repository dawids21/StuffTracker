package xyz.stasiak.stufftracker.ui.item.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.data.Item
import xyz.stasiak.stufftracker.data.ItemsRepository
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ItemDetailsBody(item: Item, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
}

@Preview(showBackground = true)
@Composable
fun ItemDetailsBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ItemDetailsBody(ItemsRepository.getItems().first())
    }
}