package xyz.stasiak.stufftracker.data

import androidx.annotation.DrawableRes

data class Item(
    val id: Int,
    val name: String,
    val category: String,
    val numOfItems: Int,
    val currentUses: Int,
    val usesLeft: Int,
    val usesPerItem: Int,
    @DrawableRes val image: Int
) {
    companion object {
        val Empty = Item(
            id = 0,
            name = "",
            category = "",
            numOfItems = 0,
            currentUses = 0,
            usesLeft = 0,
            usesPerItem = 0,
            image = 0
        )
    }
}