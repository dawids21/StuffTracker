package xyz.stasiak.stufftracker.data.itemcalculation

import xyz.stasiak.stufftracker.data.product.ProductService

class ItemCalculationService(
    private val itemCalculationRepository: ItemCalculationRepository,
    private val productService: ProductService
) {
    suspend fun useItem(productId: Int) {
        var itemCalculation = itemCalculationRepository.getItemCalculation(productId)
        if (itemCalculation == null) {
            itemCalculation = ItemCalculation(
                productId = productId,
                itemUses = 0,
                isFinished = false
            )
            itemCalculationRepository.insert(itemCalculation)
        } else {
            itemCalculationRepository.update(
                itemCalculation.copy(
                    itemUses = itemCalculation.itemUses + 1
                )
            )
        }
        productService.updateProductItemUses(itemCalculation)
    }
}