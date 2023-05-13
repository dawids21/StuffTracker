package xyz.stasiak.stufftracker.data.itemcalculation

import kotlinx.coroutines.flow.Flow

class OfflineItemCalculationRepository(private val itemCalculationDao: ItemCalculationDao) :
    ItemCalculationRepository {
    override fun getItemCalculation(productId: Int): Flow<ItemCalculation> =
        itemCalculationDao.getItemCalculation(productId)

    override suspend fun insert(itemCalculation: ItemCalculation) =
        itemCalculationDao.insert(itemCalculation)

    override suspend fun update(itemCalculation: ItemCalculation) =
        itemCalculationDao.update(itemCalculation)

    override suspend fun delete(itemCalculation: ItemCalculation) =
        itemCalculationDao.delete(itemCalculation)

    override suspend fun deleteByProductId(productId: Int) =
        itemCalculationDao.deleteByProductId(productId)
}
