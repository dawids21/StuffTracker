package xyz.stasiak.stufftracker.data.itemcalculation

import xyz.stasiak.stufftracker.data.product.ProductService

class ItemCalculationService(
    private val itemCalculationRepository: ItemCalculationRepository,
    private val productService: ProductService
) {
    suspend fun useItem(productId: Int) {
        var itemCalculation = itemCalculationRepository.getUnfinishedItemCalculation(productId)
        if (itemCalculation == null) {
            itemCalculation = ItemCalculation(
                productId = productId,
                itemUses = 1,
                isFinished = false
            )
            itemCalculationRepository.insert(itemCalculation)
        } else {
            itemCalculation = itemCalculation.copy(itemUses = itemCalculation.itemUses + 1)
            itemCalculationRepository.update(itemCalculation)
        }
        productService.updateProductItemUses(itemCalculation)
    }
}