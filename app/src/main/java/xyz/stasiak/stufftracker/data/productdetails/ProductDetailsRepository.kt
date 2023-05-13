package xyz.stasiak.stufftracker.data.productdetails

import kotlinx.coroutines.flow.Flow

interface ProductDetailsRepository {
    fun getProductDetails(productId: Int): Flow<ProductDetails>

    suspend fun insert(productDetails: ProductDetails): Long

    suspend fun update(productDetails: ProductDetails)

    suspend fun delete(productDetails: ProductDetails)

    suspend fun deleteById(id: Int)
}