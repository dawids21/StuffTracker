package xyz.stasiak.stufftracker.data.productdetails

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDetailsDao {
    @Query("SELECT * FROM product_details WHERE id = :id")
    fun getProductDetails(id: Int): Flow<ProductDetails>

    @Insert
    suspend fun insert(productDetails: ProductDetails): Long

    @Update
    suspend fun update(productDetails: ProductDetails)

    @Delete
    suspend fun delete(productDetails: ProductDetails)
}