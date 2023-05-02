package xyz.stasiak.stufftracker.ui.item.entry

import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.data.Item

data class ItemEntryUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val status: ItemUiStatus = ItemUiStatus.EDIT,
)

data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val nameValid: Boolean = true,
    val category: String = "",
    val categoryValid: Boolean = true,
    val numOfItems: String = "",
    val numOfItemsValid: Boolean = true,
    val currentUses: String = "",
    val currentUsesValid: Boolean = true,
    val usesLeft: String = "",
    val usesLeftValid: Boolean = true,
    val usesPerItem: String = "",
    val usesPerItemValid: Boolean = true,
) {
    fun toItem(): Item {
        return Item(
            id = id,
            name = name,
            category = category,
            numOfItems = numOfItems.toInt(),
            currentUses = currentUses.toInt(),
            usesLeft = usesLeft.toInt(),
            usesPerItem = usesPerItem.toInt(),
            image = R.drawable.shampoo
        )
    }
}

fun Item.toItemDetails() = ItemDetails(
    id = id,
    name = name,
    nameValid = true,
    category = category,
    categoryValid = true,
    numOfItems = numOfItems.toString(),
    numOfItemsValid = true,
    currentUses = currentUses.toString(),
    currentUsesValid = true,
    usesLeft = usesLeft.toString(),
    usesLeftValid = true,
    usesPerItem = usesPerItem.toString(),
    usesPerItemValid = true,
)

enum class ItemUiStatus {
    EDIT, SAVED
}