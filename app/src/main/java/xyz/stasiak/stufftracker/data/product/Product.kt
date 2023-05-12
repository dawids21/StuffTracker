package xyz.stasiak.stufftracker.data.product

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val name: String,
    val category: String?,
    val numOfItems: Int,
    val lastItemUses: Int,
    val averageUses: Float,
    val isCalculated: Boolean,
    @DrawableRes val image: Int
)
