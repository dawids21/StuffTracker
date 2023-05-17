package xyz.stasiak.stufftracker.data.product

import kotlinx.coroutines.flow.Flow

class OfflineProductRepository(private val productDao: ProductDao) : ProductRepository {
    override fun getProducts(userId: String): Flow<List<Product>> = productDao.getProducts(userId)

    override fun getProduct(id: Int): Flow<Product> = productDao.getProduct(id)

    override fun getProductFlowByProductId(productId: Int): Flow<Product> =
        productDao.getProductFlowByProductId(productId)

    override suspend fun getProductByProductId(productId: Int): Product =
        productDao.getProductByProductId(productId)

    override suspend fun insert(product: Product) = productDao.insert(product)

    override suspend fun update(product: Product) = productDao.update(product)

    override suspend fun delete(product: Product) = productDao.delete(product)
}