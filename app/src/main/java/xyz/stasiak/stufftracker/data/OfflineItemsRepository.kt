package xyz.stasiak.stufftracker.data

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    override fun getItems(): Flow<List<Item>> = itemDao.getItems()
}
