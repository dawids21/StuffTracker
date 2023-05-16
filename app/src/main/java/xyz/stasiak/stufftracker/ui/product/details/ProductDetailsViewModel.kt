package xyz.stasiak.stufftracker.ui.product.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculationRepository
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculationService
import xyz.stasiak.stufftracker.data.product.ProductRepository
import xyz.stasiak.stufftracker.data.product.ProductService
import xyz.stasiak.stufftracker.data.productdetails.ProductDetailsRepository
import xyz.stasiak.stufftracker.ui.RemindDialogState

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val productDetailsRepository: ProductDetailsRepository,
    private val itemCalculationRepository: ItemCalculationRepository,
    private val itemCalculationService: ItemCalculationService,
    private val productService: ProductService
) : ViewModel() {

    var uiState by mutableStateOf<ProductDetailsUiState>(ProductDetailsUiState.Loading)
        private set

    var remindDialogState by mutableStateOf<RemindDialogState>(RemindDialogState.Hidden)
        private set

    init {
        viewModelScope.launch {
            savedStateHandle.getStateFlow<Int?>(ProductDetailsDestination.productIdArg, null)
                .filterNotNull()
                .flatMapLatest { productRepository.getProductFlowByProductId(it) }
                .mapLatest { product ->
                    if (product == null) {
                        ProductDetailsUiState.NavigateBack
                    } else {
                        ProductDetailsUiState.Content(product)
                    }
                }
                .collect {
                    uiState = it
                }
        }
    }

    fun useItem(productId: Int) {
        viewModelScope.launch {
            val itemCalculation = itemCalculationService.useItem(productId)
            productService.onUseItem(itemCalculation)
            val product = productRepository.getProductByProductId(productId)
            if (!product.remindDialogShown && product.numOfItems == 1 &&
                (itemCalculation.itemUses > product.averageUses * 0.8 || product.averageUses - itemCalculation.itemUses < 2.5)
            ) {
                remindDialogState = RemindDialogState.Showing(product.name)
                productService.onRemindDialogShown(product.productId)
            }
        }
    }

    fun onDeleteClicked() {
        if (uiState is ProductDetailsUiState.Content) {
            val product = (uiState as ProductDetailsUiState.Content).product
            viewModelScope.launch {
                productRepository.delete(product)
                productDetailsRepository.deleteById(product.productId)
                itemCalculationRepository.deleteByProductId(product.productId)
            }
        }
    }

    fun onResetClicked() {
        if (uiState is ProductDetailsUiState.Content) {
            val product = (uiState as ProductDetailsUiState.Content).product
            viewModelScope.launch {
                itemCalculationService.resetItemCalculations(product.productId)
                productService.onProductItemReset(product.productId)
            }
        }
    }

    fun onRemindDialogDismissed() {
        remindDialogState = RemindDialogState.Hidden
    }
}