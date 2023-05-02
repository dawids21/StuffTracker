package xyz.stasiak.stufftracker.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getItems(): Flow<List<Item>>

    fun getItem(id: Int): Flow<Item>

    suspend fun saveItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun updateItem(item: Item)
}