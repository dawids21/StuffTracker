package xyz.stasiak.stufftracker.ui.item.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.data.MockItemsRepository
import xyz.stasiak.stufftracker.ui.LoadingIndicator
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ItemDetailsBody(uiState: ItemDetailsUiState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (uiState) {
            is ItemDetailsUiState.Loading -> LoadingIndicator()
            is ItemDetailsUiState.Content -> ItemDetailsBodyContent(uiState.item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemDetailsBodyLoadingPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ItemDetailsBody(ItemDetailsUiState.Loading)
    }
}

@Preview(showBackground = true)
@Composable
fun ItemDetailsBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ItemDetailsBody(ItemDetailsUiState.Content(MockItemsRepository.getItems().first()))
    }
}