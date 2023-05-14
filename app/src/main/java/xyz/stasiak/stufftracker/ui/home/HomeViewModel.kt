package xyz.stasiak.stufftracker.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.data.category.CategoryRepository
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculationService
import xyz.stasiak.stufftracker.data.product.Product
import xyz.stasiak.stufftracker.data.product.ProductRepository
import xyz.stasiak.stufftracker.data.product.ProductService
import xyz.stasiak.stufftracker.data.productdetails.ProductDetailsRepository

class HomeViewModel(
    productRepository: ProductRepository,
    private val productService: ProductService,
    categoryRepository: CategoryRepository,
    private val itemCalculationService: ItemCalculationService,
    private val productDetailsRepository: ProductDetailsRepository,
) :
    ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val products = productRepository.getProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())
    val categories = categoryRepository.getCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())
    var toastShowState by mutableStateOf<ToastShowState>(ToastShowState.Hide)
        private set

    fun useItem(productId: Int) {
        viewModelScope.launch {
            itemCalculationService.useItem(productId)
        }
    }

    fun depleteItem(product: Product) {
        if (product.numOfItems == 0) {
            toastShowState = ToastShowState.Show(product.name)
            return
        }
        viewModelScope.launch {
            val productDetails = productDetailsRepository.getProductDetails(product.id)
            val newProductDetails = productDetails.copy(numOfItems = productDetails.numOfItems - 1)
            productDetailsRepository.update(newProductDetails)
            productService.onProductItemDepleted(newProductDetails)
        }
    }

    fun onToastShown() {
        toastShowState = ToastShowState.Hide
    }
}