package xyz.stasiak.stufftracker.ui.settings.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.auth.GoogleAuthUiClient
import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.data.category.CategoryRepository
import xyz.stasiak.stufftracker.data.product.ProductService
import xyz.stasiak.stufftracker.data.productdetails.ProductDetailsRepository

class CategorySettingsViewModel(
    private val categoryRepository: CategoryRepository,
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val productDetailsRepository: ProductDetailsRepository,
    private val productService: ProductService
) : ViewModel() {

    val categories =
        categoryRepository.getCategories(googleAuthUiClient.getSignedInUser()?.id ?: "")
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList()
            )

    fun addCategory(name: String) {
        if (name.isBlank()) {
            return
        }
        viewModelScope.launch {
            val userId = googleAuthUiClient.getSignedInUser()?.id ?: return@launch
            categoryRepository.saveCategory(Category(name = name, userId = userId))
        }
    }

    fun editCategory(category: Category, newName: String) {
        val userId = googleAuthUiClient.getSignedInUser()?.id ?: return
        if (newName.isBlank()) {
            return
        }
        viewModelScope.launch {
            val newCategory = category.copy(name = newName)
            categoryRepository.updateCategory(newCategory)
            val productsDetails = productDetailsRepository.getProductDetailsByCategoryId(
                category.id,
                userId
            )
            productService.onCategoryNameChanged(newCategory, productsDetails)
        }
    }

    fun deleteCategory(category: Category) {
        val userId = googleAuthUiClient.getSignedInUser()?.id ?: return
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
            val productsDetails = productDetailsRepository.getProductDetailsByCategoryId(
                category.id,
                userId
            )
            for (productDetails in productsDetails) {
                productDetailsRepository.update(productDetails.copy(categoryId = null))
            }
            productService.onCategoryDeleted(productsDetails)
        }
    }
}