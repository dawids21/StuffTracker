package xyz.stasiak.stufftracker.ui.settings.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import xyz.stasiak.stufftracker.data.CategoryRepository

class CategorySettingsViewModel(categoryRepository: CategoryRepository) : ViewModel() {

    val categories = categoryRepository.getCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )
}