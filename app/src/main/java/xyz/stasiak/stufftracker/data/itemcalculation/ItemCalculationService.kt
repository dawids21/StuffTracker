package xyz.stasiak.stufftracker.data.itemcalculation

class ItemCalculationService(
    private val itemCalculationRepository: ItemCalculationRepository
) {
    suspend fun useItem(productId: Int): ItemCalculation {
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
        return itemCalculation
    }

    suspend fun finishItemCalculation(productId: Int): List<ItemCalculation> {
        var itemCalculation = itemCalculationRepository.getUnfinishedItemCalculation(productId)
        if (itemCalculation == null) {
            itemCalculation = ItemCalculation(
                productId = productId,
                itemUses = 0,
                isFinished = true
            )
            itemCalculationRepository.insert(itemCalculation)
        } else {
            itemCalculation = itemCalculation.copy(isFinished = true)
            itemCalculationRepository.update(itemCalculation)
        }
        return itemCalculationRepository.getItemCalculations(productId)
    }
}