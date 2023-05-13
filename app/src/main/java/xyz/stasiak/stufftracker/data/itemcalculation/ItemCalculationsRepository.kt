package xyz.stasiak.stufftracker.data.itemcalculation

import kotlinx.coroutines.flow.Flow

interface ItemCalculationsRepository {
    fun getItemCalculation(productId: Int): Flow<ItemCalculation>

    suspend fun insert(itemCalculation: ItemCalculation)

    suspend fun update(itemCalculation: ItemCalculation)

    suspend fun delete(itemCalculation: ItemCalculation)

    suspend fun deleteByProductId(productId: Int)
}