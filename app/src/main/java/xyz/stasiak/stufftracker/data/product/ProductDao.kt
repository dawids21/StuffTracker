package xyz.stasiak.stufftracker.data.product

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProduct(id: Int): Flow<Product>

    @Query("SELECT * FROM products WHERE productId = :productId")
    fun getProductByProductId(productId: Int): Product

    @Query("SELECT * FROM products WHERE productId = :productId")
    fun getProductFlowByProductId(productId: Int): Flow<Product>

    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)
}