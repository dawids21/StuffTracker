package xyz.stasiak.stufftracker.data.productdetails

import kotlinx.coroutines.flow.Flow

interface ProductDetailsRepository {
    fun getProductDetailsFlow(productId: Int): Flow<ProductDetails>

    suspend fun getProductDetails(productId: Int): ProductDetails

    suspend fun getProductDetailsByCategoryId(categoryId: Int, userId: String): List<ProductDetails>

    suspend fun insert(productDetails: ProductDetails): Long

    suspend fun update(productDetails: ProductDetails)

    suspend fun delete(productDetails: ProductDetails)

    suspend fun deleteById(id: Int)
}