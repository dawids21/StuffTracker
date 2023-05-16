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
import xyz.stasiak.stufftracker.data.product.Product
import xyz.stasiak.stufftracker.data.product.ProductRepository
import xyz.stasiak.stufftracker.data.product.ProductService
import xyz.stasiak.stufftracker.data.productdetails.ProductDetailsRepository
import xyz.stasiak.stufftracker.ui.DialogState
import xyz.stasiak.stufftracker.ui.home.ToastShowState

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

    var toastShowState by mutableStateOf<ToastShowState>(ToastShowState.Hide)
        private set

    var remindDialogState by mutableStateOf<DialogState>(DialogState.Hidden)
        private set

    var depleteDialogState by mutableStateOf<DialogState>(DialogState.Hidden)
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
            if (product.isCalculated) {
                if (!product.depletedDialogShown && product.averageUses - itemCalculation.itemUses < 1) {
                    depleteDialogState = DialogState.Showing(product)
                    productService.onDepleteDialogShown(product.productId)
                } else if (!product.remindDialogShown &&
                    (itemCalculation.itemUses > product.averageUses * 0.8 || product.averageUses - itemCalculation.itemUses < 2.5)
                ) {
                    remindDialogState = DialogState.Showing(product)
                    productService.onRemindDialogShown(product.productId)
                }
            }
        }
    }

    fun depleteItem(product: Product) {
        if (product.numOfItems <= 0) {
            toastShowState = ToastShowState.Show(product.name)
            return
        }
        viewModelScope.launch {
            val productDetails = productDetailsRepository.getProductDetails(product.id)
            val newProductDetails = productDetails.copy(numOfItems = productDetails.numOfItems - 1)
            productDetailsRepository.update(newProductDetails)
            productService.onProductItemDepleted(newProductDetails)
            val itemCalculations = itemCalculationService.finishItemCalculation(product.id)
            productService.onItemCalculated(product.id, itemCalculations)
        }
    }

    fun buyProduct(product: Product) {
        viewModelScope.launch {
            val productDetails = productDetailsRepository.getProductDetails(product.id)
            val newProductDetails = productDetails.copy(numOfItems = productDetails.numOfItems + 1)
            productDetailsRepository.update(newProductDetails)
            productService.onProductItemBought(newProductDetails)
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

    fun onToastShown() {
        toastShowState = ToastShowState.Hide
    }

    fun onRemindDialogDismissed() {
        remindDialogState = DialogState.Hidden
    }

    fun onDepleteDialogDismissed() {
        depleteDialogState = DialogState.Hidden
    }
}