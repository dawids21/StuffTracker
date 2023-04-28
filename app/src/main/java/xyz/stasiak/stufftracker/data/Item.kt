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
)