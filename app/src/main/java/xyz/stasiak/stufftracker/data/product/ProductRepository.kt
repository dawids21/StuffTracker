package xyz.stasiak.stufftracker.data.product

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(userId: String): Flow<List<Product>>

    fun getProduct(id: Int): Flow<Product>

    suspend fun getProductByProductId(productId: Int): Product

    fun getProductFlowByProductId(productId: Int): Flow<Product?>

    suspend fun insert(product: Product)

    suspend fun update(product: Product)

    suspend fun delete(product: Product)
}