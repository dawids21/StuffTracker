package xyz.stasiak.stufftracker.ui.product.entry

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
    val image: String? = null,
) {
    fun toProductDetails(userId: String): ProductDetails {
        return ProductDetails(
            id = id,
            name = name,
            categoryId = categoryId,
            numOfItems = numOfItems.toInt(),
            image = image,
            userId = userId
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
    image = image,
)

enum class ProductUiStatus {
    EDIT, SAVED
}