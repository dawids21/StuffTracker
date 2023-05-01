package xyz.stasiak.stufftracker.ui.item.details

import xyz.stasiak.stufftracker.data.Item

sealed interface ItemDetailsUiState {
    object Loading : ItemDetailsUiState
    data class Content(val item: Item) : ItemDetailsUiState
}