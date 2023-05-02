package xyz.stasiak.stufftracker.ui.item.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import xyz.stasiak.stufftracker.data.ItemsRepository

class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    itemsRepository: ItemsRepository
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = savedStateHandle.getStateFlow<Int?>(ItemDetailsDestination.itemIdArg, null)
        .filterNotNull()
        .flatMapLatest { itemsRepository.getItem(it) }
        .mapLatest { ItemDetailsUiState.Content(it) }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ItemDetailsUiState.Loading
        )
}