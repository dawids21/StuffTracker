package xyz.stasiak.stufftracker.ui

import xyz.stasiak.stufftracker.data.product.Product

sealed interface DialogState {
    object Hidden : DialogState
    data class Showing(val product: Product) : DialogState
}