package xyz.stasiak.stufftracker.data.productdetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_details")
data class ProductDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val categoryId: Int?,
    val numOfItems: Int,
    val image: String?
)
