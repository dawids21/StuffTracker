package xyz.stasiak.stufftracker.ui.product.entry

import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.data.productdetails.ProductDetails

data class ProductEntryUiState(
    val productDetailsEntry: ProductDetailsEntry = ProductDetailsEntry(),
    val status: ProductUiStatus = ProductUiStatus.EDIT,
)

data class ProductDetailsEntry(
    val id: Int = 0,
    val name: String = "",
    val nameValid: Boolean = true,
    val categoryId: Int? = null,
    val categoryValid: Boolean = true,
    val numOfItems: String = "",
    val numOfItemsValid: Boolean = true,
) {
    fun toProductDetails(): ProductDetails {
        return ProductDetails(
            id = id,
            name = name,
            categoryId = categoryId,
            numOfItems = numOfItems.toInt(),
            image = R.drawable.shampoo
        )
    }
}

fun ProductDetails.toProductDetailsEntry() = ProductDetailsEntry(
    id = id,
    name = name,
    nameValid = true,
    categoryId = categoryId,
    categoryValid = true,
    numOfItems = numOfItems.toString(),
    numOfItemsValid = true,
)

enum class ProductUiStatus {
    EDIT, SAVED
}