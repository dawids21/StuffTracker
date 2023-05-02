package xyz.stasiak.stufftracker.data

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val numOfItems: Int,
    val currentUses: Int,
    val usesLeft: Int,
    val usesPerItem: Int,
    @DrawableRes val image: Int
)