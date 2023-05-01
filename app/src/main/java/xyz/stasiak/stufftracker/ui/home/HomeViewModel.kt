package xyz.stasiak.stufftracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import xyz.stasiak.stufftracker.data.ItemsRepository

class HomeViewModel(itemsRepository: ItemsRepository) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val items = itemsRepository.getItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())
}