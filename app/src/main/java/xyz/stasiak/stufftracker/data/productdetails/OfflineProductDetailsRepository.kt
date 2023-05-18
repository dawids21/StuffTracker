package xyz.stasiak.stufftracker.data.productdetails

import kotlinx.coroutines.flow.Flow

class OfflineProductDetailsRepository(private val productDetailsDao: ProductDetailsDao) :
    ProductDetailsRepository {
    override fun getProductDetailsFlow(productId: Int): Flow<ProductDetails> =
        productDetailsDao.getProductDetailsFlow(productId)

    override suspend fun getProductDetails(productId: Int): ProductDetails =
        productDetailsDao.getProductDetails(productId)

    override suspend fun getProductDetailsByCategoryId(
        categoryId: Int,
        userId: String
    ): List<ProductDetails> =
        productDetailsDao.getProductDetailsByCategoryId(categoryId, userId)

    override suspend fun insert(productDetails: ProductDetails) =
        productDetailsDao.insert(productDetails)

    override suspend fun update(productDetails: ProductDetails) =
        productDetailsDao.update(productDetails)

    override suspend fun delete(productDetails: ProductDetails) =
        productDetailsDao.delete(productDetails)

    override suspend fun deleteById(id: Int) =
        productDetailsDao.deleteById(id)
}