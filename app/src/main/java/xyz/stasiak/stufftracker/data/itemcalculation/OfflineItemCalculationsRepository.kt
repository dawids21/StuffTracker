package xyz.stasiak.stufftracker.data.itemcalculation

import kotlinx.coroutines.flow.Flow

class OfflineItemCalculationsRepository(private val itemCalculationDao: ItemCalculationDao) :
    ItemCalculationsRepository {
    override fun getItemCalculation(productId: Int): Flow<ItemCalculation> =
        itemCalculationDao.getItemCalculation(productId)

    override suspend fun insert(itemCalculation: ItemCalculation) =
        itemCalculationDao.insert(itemCalculation)

    override suspend fun update(itemCalculation: ItemCalculation) =
        itemCalculationDao.update(itemCalculation)

    override suspend fun delete(itemCalculation: ItemCalculation) =
        itemCalculationDao.delete(itemCalculation)
}
