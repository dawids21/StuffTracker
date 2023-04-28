package xyz.stasiak.stufftracker.ui.item.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.stasiak.stufftracker.data.Item
import xyz.stasiak.stufftracker.data.ItemsRepository
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ItemDetailsBody(item: Item, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = item.name)
    }
}

@Preview(showBackground = true)
@Composable
fun ItemDetailsBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ItemDetailsBody(ItemsRepository.getItems().first())
    }
}