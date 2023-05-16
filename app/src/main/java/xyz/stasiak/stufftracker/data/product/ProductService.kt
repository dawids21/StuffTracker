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
                image = productDetails.image,
                remindDialogShown = false
            )
        )
    }

    suspend fun onUseItem(itemCalculation: ItemCalculation) {
        val product = productRepository.getProductByProductId(itemCalculation.productId)
        productRepository.update(
            product.copy(lastItemUses = itemCalculation.itemUses)
        )
    }

    suspend fun onItemCalculated(productId: Int, itemCalculations: List<ItemCalculation>) {
        val product = productRepository.getProductByProductId(productId)
        productRepository.update(
            product.copy(
                averageUses = itemCalculations.map { it.itemUses }.average().toFloat(),
                isCalculated = true
            )
        )
    }

    suspend fun onProductItemDepleted(productDetails: ProductDetails) {
        val product = productRepository.getProductByProductId(productDetails.id)
        productRepository.update(
            product.copy(
                numOfItems = productDetails.numOfItems,
                lastItemUses = 0,
                remindDialogShown = false
            )
        )
    }

    suspend fun onProductItemBought(productDetails: ProductDetails) {
        val product = productRepository.getProductByProductId(productDetails.id)
        productRepository.update(
            product.copy(
                numOfItems = productDetails.numOfItems,
                remindDialogShown = false
            )
        )
    }

    suspend fun onProductItemReset(productId: Int) {
        val product = productRepository.getProductByProductId(productId)
        productRepository.update(
            product.copy(
                averageUses = 0f,
                isCalculated = false,
                remindDialogShown = false
            )
        )
    }

    suspend fun onRemindDialogShown(productId: Int) {
        val product = productRepository.getProductByProductId(productId)
        productRepository.update(
            product.copy(
                remindDialogShown = true
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
    remindDialogShown = false,
    image = image
)
