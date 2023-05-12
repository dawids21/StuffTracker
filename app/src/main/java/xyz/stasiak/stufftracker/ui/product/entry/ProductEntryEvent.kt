package xyz.stasiak.stufftracker.ui.product.entry

sealed interface ProductEntryEvent {
    data class NameChanged(val name: String) : ProductEntryEvent
    data class CategoryChanged(val categoryId: Int?) : ProductEntryEvent
    data class NumOfItemsChanged(val numOfItems: String) : ProductEntryEvent
    object SaveClicked : ProductEntryEvent
}