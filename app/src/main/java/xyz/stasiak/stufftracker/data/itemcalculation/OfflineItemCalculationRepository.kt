package xyz.stasiak.stufftracker.data.itemcalculation

class OfflineItemCalculationRepository(private val itemCalculationDao: ItemCalculationDao) :
    ItemCalculationRepository {
    override suspend fun getUnfinishedItemCalculation(productId: Int): ItemCalculation? =
        itemCalculationDao.getUnfinishedItemCalculation(productId)

    override suspend fun getItemCalculations(productId: Int): List<ItemCalculation> =
        itemCalculationDao.getItemCalculations(productId)

    override suspend fun getFinishedItemCalculations(productId: Int): List<ItemCalculation> =
        itemCalculationDao.getFinishedItemCalculations(productId)

    override suspend fun insert(itemCalculation: ItemCalculation) =
        itemCalculationDao.insert(itemCalculation)

    override suspend fun update(itemCalculation: ItemCalculation) =
        itemCalculationDao.update(itemCalculation)

    override suspend fun delete(itemCalculation: ItemCalculation) =
        itemCalculationDao.delete(itemCalculation)

    override suspend fun deleteByProductId(productId: Int) =
        itemCalculationDao.deleteByProductId(productId)
}
