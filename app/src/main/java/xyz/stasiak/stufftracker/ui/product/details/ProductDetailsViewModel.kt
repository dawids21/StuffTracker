package xyz.stasiak.stufftracker.ui.product.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import xyz.stasiak.stufftracker.data.product.ProductRepository

class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    productRepository: ProductRepository
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = savedStateHandle.getStateFlow<Int?>(ProductDetailsDestination.productIdArg, null)
        .filterNotNull()
        .flatMapLatest { productRepository.getProductFlowByProductId(it) }
        .mapLatest { ProductDetailsUiState.Content(it) }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProductDetailsUiState.Loading
        )
}