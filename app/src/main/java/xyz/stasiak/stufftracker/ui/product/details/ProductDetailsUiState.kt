package xyz.stasiak.stufftracker.ui.product.details

import xyz.stasiak.stufftracker.data.product.Product

sealed interface ProductDetailsUiState {
    object Loading : ProductDetailsUiState
    data class Content(val product: Product) : ProductDetailsUiState
    object NavigateBack : ProductDetailsUiState
}