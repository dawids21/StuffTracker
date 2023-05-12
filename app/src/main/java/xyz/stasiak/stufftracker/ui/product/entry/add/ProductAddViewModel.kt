package xyz.stasiak.stufftracker.ui.product.entry.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.data.category.CategoryRepository
import xyz.stasiak.stufftracker.data.product.ProductService
import xyz.stasiak.stufftracker.data.productdetails.ProductDetailsRepository
import xyz.stasiak.stufftracker.ui.product.entry.ProductEntryEvent
import xyz.stasiak.stufftracker.ui.product.entry.ProductEntryUiState
import xyz.stasiak.stufftracker.ui.product.entry.ProductUiStatus

class ProductAddViewModel(
    private val productDetailsRepository: ProductDetailsRepository,
    private val categoryRepository: CategoryRepository,
    private val productService: ProductService
) : ViewModel() {
    var productEntryUiState by mutableStateOf(ProductEntryUiState())
        private set

    var categories by mutableStateOf<List<Category>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            categoryRepository.getCategories().collect {
                categories = it
            }
        }
    }

    fun handleEvent(event: ProductEntryEvent) {
        when (event) {
            is ProductEntryEvent.NameChanged -> {
                productEntryUiState = productEntryUiState.run {
                    copy(
                        productDetailsEntry = productDetailsEntry.copy(
                            name = event.name,
                            nameValid = validateName(event.name)
                        ),
                    )
                }
            }

            is ProductEntryEvent.CategoryChanged -> {
                productEntryUiState = productEntryUiState.run {
                    copy(
                        productDetailsEntry = productDetailsEntry.copy(
                            categoryId = event.categoryId,
                            categoryValid = true
                        ),
                    )
                }
            }

            is ProductEntryEvent.NumOfItemsChanged -> {
                productEntryUiState = productEntryUiState.run {
                    copy(
                        productDetailsEntry = productDetailsEntry.copy(
                            numOfItems = event.numOfItems,
                            numOfItemsValid = validateNumOfItems(event.numOfItems)
                        ),
                    )
                }
            }

            is ProductEntryEvent.SaveClicked -> {
                if (validateDetails()) {
                    viewModelScope.launch {
                        val productDetails =
                            productEntryUiState.productDetailsEntry.toProductDetails()
                        val id = productDetailsRepository.insert(productDetails)
                        val productDetailsWithId = productDetails.copy(id = id.toInt())
                        withContext(Dispatchers.IO) {
                            productService.createProduct(productDetailsWithId)
                        }
                        productEntryUiState =
                            productEntryUiState.copy(status = ProductUiStatus.SAVED)
                    }
                }
            }
        }
    }

    private fun validateDetails(): Boolean {
        return validateName(productEntryUiState.productDetailsEntry.name) &&
                validateNumOfItems(productEntryUiState.productDetailsEntry.numOfItems)
    }

    private fun validateName(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun validateNumOfItems(numOfItems: String): Boolean {
        return numOfItems.isNotBlank() && numOfItems.toIntOrNull() != null && numOfItems.toInt() >= 0
    }
}