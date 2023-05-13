package xyz.stasiak.stufftracker.data.itemcalculation

interface ItemCalculationRepository {
    fun getItemCalculation(productId: Int): ItemCalculation?

    suspend fun insert(itemCalculation: ItemCalculation)

    suspend fun update(itemCalculation: ItemCalculation)

    suspend fun delete(itemCalculation: ItemCalculation)

    suspend fun deleteByProductId(productId: Int)
}