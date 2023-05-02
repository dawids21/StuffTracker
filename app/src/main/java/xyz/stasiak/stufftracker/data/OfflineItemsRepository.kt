package xyz.stasiak.stufftracker.data

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    override fun getItems(): Flow<List<Item>> = itemDao.getItems()
    override fun getItem(id: Int): Flow<Item> = itemDao.getItem(id)
    override suspend fun saveItem(item: Item) = itemDao.insert(item)
    override suspend fun deleteItem(item: Item) = itemDao.delete(item)
    override suspend fun updateItem(item: Item) = itemDao.update(item)
}
