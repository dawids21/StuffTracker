package xyz.stasiak.stufftracker.ui.settings.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.data.Category
import xyz.stasiak.stufftracker.data.CategoryRepository

class CategorySettingsViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    val categories = categoryRepository.getCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    fun addCategory(name: String) {
        viewModelScope.launch {
            categoryRepository.saveCategory(Category(name = name))
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }
}