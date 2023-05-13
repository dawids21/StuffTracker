package xyz.stasiak.stufftracker.data.product

import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.data.category.CategoryRepository
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculation
import xyz.stasiak.stufftracker.data.productdetails.ProductDetails

class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) {

    suspend fun createProduct(productDetails: ProductDetails) {
        val category = productDetails.categoryId?.let { categoryRepository.getCategory(it) }
        productRepository.insert(productDetails.toProduct(category))
    }

    suspend fun updateProductDetails(productDetails: ProductDetails) {
        val product = productRepository.getProductByProductId(productDetails.id)
        val category = productDetails.categoryId?.let { categoryRepository.getCategory(it) }
        productRepository.update(
            product.copy(
                name = productDetails.name,
                category = category?.name,
                numOfItems = productDetails.numOfItems,
                image = productDetails.image
            )
        )
    }

    suspend fun updateProductItemUses(itemCalculation: ItemCalculation) {
        val product = productRepository.getProductByProductId(itemCalculation.productId)
        productRepository.update(
            product.copy(lastItemUses = itemCalculation.itemUses)
        )
    }

    suspend fun calculateProduct(itemCalculation: ItemCalculation) {
        val product = productRepository.getProductByProductId(itemCalculation.productId)
        productRepository.update(
            product.copy(
                averageUses = itemCalculation.itemUses.toFloat(),
                isCalculated = true
            )
        )
    }
}

private fun ProductDetails.toProduct(category: Category?) = Product(
    productId = id,
    name = name,
    category = category?.name,
    numOfItems = numOfItems,
    lastItemUses = 0,
    averageUses = 0f,
    isCalculated = false,
    image = image
)
