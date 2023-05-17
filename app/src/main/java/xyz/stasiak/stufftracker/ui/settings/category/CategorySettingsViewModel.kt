package xyz.stasiak.stufftracker.ui.settings.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.auth.GoogleAuthUiClient
import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.data.category.CategoryRepository

class CategorySettingsViewModel(
    private val categoryRepository: CategoryRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    val categories =
        categoryRepository.getCategories(googleAuthUiClient.getSignedInUser()?.id ?: "")
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList()
            )

    fun addCategory(name: String) {
        viewModelScope.launch {
            val userId = googleAuthUiClient.getSignedInUser()?.id ?: return@launch
            categoryRepository.saveCategory(Category(name = name, userId = userId))
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }
}