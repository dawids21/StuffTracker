package xyz.stasiak.stufftracker.data

import androidx.annotation.DrawableRes

data class Item(
    val id: Int,
    val name: String,
    val category: String,
    val numOfItems: Int,
    val usesLeft: Int,
    @DrawableRes val image: Int
)