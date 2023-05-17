package xyz.stasiak.stufftracker.ui.product.entry.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.auth.GoogleAuthUiClient
import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.data.category.CategoryRepository
import xyz.stasiak.stufftracker.data.product.ProductService
import xyz.stasiak.stufftracker.data.productdetails.ProductDetailsRepository
import xyz.stasiak.stufftracker.ui.product.entry.ProductEntryEvent
import xyz.stasiak.stufftracker.ui.product.entry.ProductEntryUiState
import xyz.stasiak.stufftracker.ui.product.entry.ProductUiStatus
import xyz.stasiak.stufftracker.ui.product.entry.toProductDetailsEntry

@OptIn(ExperimentalCoroutinesApi::class)
class ProductEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val productDetailsRepository: ProductDetailsRepository,
    private val categoryRepository: CategoryRepository,
    private val productService: ProductService,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {
    var productEntryUiState by mutableStateOf(ProductEntryUiState())
        private set

    var categories by mutableStateOf<List<Category>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            val userId = googleAuthUiClient.getSignedInUser()?.id ?: ""
            productEntryUiState =
                savedStateHandle.getStateFlow<Int?>(ProductEditDestination.productIdArg, null)
                    .filterNotNull()
                    .flatMapLatest { productDetailsRepository.getProductDetailsFlow(it) }
                    .map { ProductEntryUiState(productDetailsEntry = it.toProductDetailsEntry()) }
                    .first()

            categoryRepository.getCategories(userId).collect {
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

            is ProductEntryEvent.ImageChanged -> {
                productEntryUiState = productEntryUiState.run {
                    copy(
                        productDetailsEntry = productDetailsEntry.copy(
                            image = event.image
                        ),
                    )
                }
            }

            is ProductEntryEvent.SaveClicked -> {
                if (validateDetails()) {
                    viewModelScope.launch {
                        val userId = googleAuthUiClient.getSignedInUser()?.id ?: return@launch
                        val productDetails =
                            productEntryUiState.productDetailsEntry.toProductDetails(userId)
                        productDetailsRepository.update(productDetails)
                        productService.updateProductDetails(productDetails)
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