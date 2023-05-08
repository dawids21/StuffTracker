package xyz.stasiak.stufftracker.ui.settings.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import xyz.stasiak.stufftracker.data.ItemsRepository

class CategorySettingsViewModel(itemsRepository: ItemsRepository) : ViewModel() {

    val categories =
        itemsRepository.getItems().map { flow ->
            flow.map { it.category }
                .distinct()
                .sorted()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )
}