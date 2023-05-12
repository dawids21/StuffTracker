package xyz.stasiak.stufftracker.data.product

import kotlinx.coroutines.flow.Flow

class OfflineProductRepository(private val productDao: ProductDao) : ProductRepository {
    override fun getProducts(): Flow<List<Product>> = productDao.getProducts()

    override fun getProduct(id: Int): Flow<Product> = productDao.getProduct(id)

    override fun getProductFlowByProductId(productId: Int): Flow<Product> =
        productDao.getProductFlowByProductId(productId)

    override fun getProductByProductId(productId: Int): Product =
        productDao.getProductByProductId(productId)

    override suspend fun insert(product: Product) = productDao.insert(product)

    override suspend fun update(product: Product) = productDao.update(product)

    override suspend fun delete(product: Product) = productDao.delete(product)
}