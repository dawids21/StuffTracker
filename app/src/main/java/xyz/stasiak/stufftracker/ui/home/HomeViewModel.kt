package xyz.stasiak.stufftracker.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.auth.GoogleAuthUiClient
import xyz.stasiak.stufftracker.data.category.CategoryRepository
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculationService
import xyz.stasiak.stufftracker.data.product.Product
import xyz.stasiak.stufftracker.data.product.ProductRepository
import xyz.stasiak.stufftracker.data.product.ProductService
import xyz.stasiak.stufftracker.data.productdetails.ProductDetailsRepository
import xyz.stasiak.stufftracker.ui.DialogState

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val productService: ProductService,
    categoryRepository: CategoryRepository,
    private val itemCalculationService: ItemCalculationService,
    private val productDetailsRepository: ProductDetailsRepository,
    googleAuthUiClient: GoogleAuthUiClient
) :
    ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val products = productRepository.getProducts(googleAuthUiClient.getSignedInUser()?.id ?: "")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())
    val categories =
        categoryRepository.getCategories(googleAuthUiClient.getSignedInUser()?.id ?: "")
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())
    var toastShowState by mutableStateOf<ToastShowState>(ToastShowState.Hide)
        private set

    var remindDialogState by mutableStateOf<DialogState>(DialogState.Hidden)
        private set

    var depleteDialogState by mutableStateOf<DialogState>(DialogState.Hidden)
        private set

    fun useItem(product: Product) {
        if (product.numOfItems <= 0) {
            toastShowState = ToastShowState.Show(product.name)
            return
        }
        viewModelScope.launch {
            val itemCalculation = itemCalculationService.useItem(product.id)
            productService.onUseItem(itemCalculation)
            val updatedProduct = productRepository.getProductByProductId(product.id)
            if (updatedProduct.isCalculated) {
                if (!updatedProduct.depletedDialogShown && updatedProduct.averageUses - itemCalculation.itemUses < 1) {
                    depleteDialogState = DialogState.Showing(updatedProduct)
                    productService.onDepleteDialogShown(updatedProduct.productId)
                } else if (!updatedProduct.remindDialogShown &&
                    (itemCalculation.itemUses > updatedProduct.averageUses * 0.8 || updatedProduct.averageUses - itemCalculation.itemUses < 2.5)
                ) {
                    remindDialogState = DialogState.Showing(updatedProduct)
                    productService.onRemindDialogShown(updatedProduct.productId)
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

    fun onToastShown() {
        toastShowState = ToastShowState.Hide
    }

    fun buyProduct(product: Product) {
        viewModelScope.launch {
            val productDetails = productDetailsRepository.getProductDetails(product.id)
            val newProductDetails = productDetails.copy(numOfItems = productDetails.numOfItems + 1)
            productDetailsRepository.update(newProductDetails)
            productService.onProductItemBought(newProductDetails)
        }
    }

    fun onRemindDialogDismissed() {
        remindDialogState = DialogState.Hidden
    }

    fun onDepleteDialogDismissed() {
        depleteDialogState = DialogState.Hidden
    }
}