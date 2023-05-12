package xyz.stasiak.stufftracker.data.productdetails

import kotlinx.coroutines.flow.Flow

class OfflineProductDetailsRepository(private val productDetailsDao: ProductDetailsDao) :
    ProductDetailsRepository {
    override fun getProductDetails(productId: Int): Flow<ProductDetails> =
        productDetailsDao.getProductDetails(productId)

    override suspend fun insert(productDetails: ProductDetails) =
        productDetailsDao.insert(productDetails)

    override suspend fun update(productDetails: ProductDetails) =
        productDetailsDao.update(productDetails)

    override suspend fun delete(productDetails: ProductDetails) =
        productDetailsDao.delete(productDetails)
}