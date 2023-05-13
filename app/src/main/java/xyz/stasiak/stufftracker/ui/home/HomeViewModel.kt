package xyz.stasiak.stufftracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.data.category.CategoryRepository
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculationService
import xyz.stasiak.stufftracker.data.product.ProductRepository

class HomeViewModel(
    productRepository: ProductRepository,
    categoryRepository: CategoryRepository,
    private val itemCalculationService: ItemCalculationService
) :
    ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val products = productRepository.getProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())
    val categories = categoryRepository.getCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())

    fun useItem(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            itemCalculationService.useItem(productId)
        }
    }
}