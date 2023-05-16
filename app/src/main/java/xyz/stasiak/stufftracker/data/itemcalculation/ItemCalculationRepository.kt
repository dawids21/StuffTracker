package xyz.stasiak.stufftracker.data.itemcalculation

interface ItemCalculationRepository {
    suspend fun getUnfinishedItemCalculation(productId: Int): ItemCalculation?

    suspend fun getItemCalculations(productId: Int): List<ItemCalculation>

    suspend fun getFinishedItemCalculations(productId: Int): List<ItemCalculation>

    suspend fun insert(itemCalculation: ItemCalculation)

    suspend fun update(itemCalculation: ItemCalculation)

    suspend fun delete(itemCalculation: ItemCalculation)

    suspend fun deleteByProductId(productId: Int)
}